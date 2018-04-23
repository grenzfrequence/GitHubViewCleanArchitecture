package com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.dto

import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.QUERY_MAX_ITEMS_PER_PAGE
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.QUERY_UPDATED_SORT

data class RequestDto(
    val userName: String,
    val pageNr: Int,
    val itemsPerPage : Int = QUERY_MAX_ITEMS_PER_PAGE,
    val sort : String = QUERY_UPDATED_SORT
)