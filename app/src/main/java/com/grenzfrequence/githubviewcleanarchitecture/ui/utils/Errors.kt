package com.grenzfrequence.githubviewcleanarchitecture.ui.utils

import android.support.annotation.DrawableRes
import com.grenzfrequence.githubviewcleanarchitecture.R
import com.grenzfrequence.githubviewcleanarchitecture.ui.common.data.*
import okhttp3.Headers


sealed class UiErrorState(open val titleId: Int, open val subtitleId: Int, @field:DrawableRes open val icon: Int) {

    class NoData(
            override val titleId: Int = R.string.error_no_data_title,
            override val subtitleId: Int = R.string.error_no_data_subtitle,
            override val icon: Int = R.drawable.ic_no_data_found
    ) : UiErrorState(titleId, subtitleId, icon)

    class NoInternet(
            override val titleId: Int = R.string.error_no_internet_title,
            override val subtitleId: Int = R.string.error_no_internet_subtitle,
            override val icon: Int = R.drawable.ic_no_internet
    ) : UiErrorState(titleId, subtitleId, icon)

    class General(
            override val titleId: Int = R.string.error_general_title,
            override val subtitleId: Int = R.string.error_general_subtitle,
            override val icon: Int = R.drawable.ic_rate_limit_exceeded
    ) : UiErrorState(titleId, subtitleId, icon)
}

fun <T> responseMapper(
        response: ResponseWrapper<T>,
        exceptionHandler: (Exception) -> Unit,
        successHandler: (payload: T, header: Headers) -> Unit) {
    when (response) {
        is Success -> {
            response.payload?.let {
                successHandler(it, response.headers)
            } ?: NoDataException()
        }
        is NoDataFound -> {
            NoDataException()
        }
        is NoInternet -> {
            NoInternetException()
        }
        is Failure -> {
            GenericErrorException(response)
        }
    }.apply {
        if (this is Exception) {
            exceptionHandler(this)
        }
    }
}

fun exceptionMapper(exception: Throwable, errorHandler: (UiErrorState) -> Unit) {
    when (exception) {
        is NoDataException -> UiErrorState.NoData()
        is NoInternetException -> UiErrorState.NoInternet()
        else -> UiErrorState.General()
    }.apply {
        errorHandler(this)
    }
}
