package com.grenzfrequence.githubviewcleanarchitecture.ui.utils

import android.net.UrlQuerySanitizer
import okhttp3.Headers
import javax.inject.Inject

class GitHubHeaderAnalyzer @Inject constructor() {

    companion object {
        private val QUERY_LINK = "Link"
        private val LAST_PAGE_INDICATOR = "rel=\"last\""
        private val PAGE_NR = "page"
    }

    fun getLastPageNr(headers: Headers): Int {
        val linkHeaderInfo: String? = headers.get(QUERY_LINK)
        var pageNr = 1

        linkHeaderInfo?.let {
            val linkValue = linkHeaderInfo.split(SPACE.toRegex())
            var i = 0
            while (i < linkValue.size) {
                val relKey = linkValue[i + 1].replace(COLON.toRegex(), "")
                if (relKey == LAST_PAGE_INDICATOR) {
                    val relContent = linkValue[i].replace(LESS_GREATER.toRegex(), "")
                    val sanitizer = UrlQuerySanitizer()
                    sanitizer.allowUnregisteredParamaters = true
                    sanitizer.parseUrl(relContent)
                    val sPageNr = sanitizer.getValue(PAGE_NR)
                    pageNr = if (sPageNr == null) 0 else Integer.parseInt(sPageNr)
                    break
                }
                i += 2
            }
        }
        return pageNr
    }
}
