package com.grenzfrequence.githubviewcleanarchitecture.ui.common.data

import com.squareup.moshi.Json
import okhttp3.Headers

sealed class ResponseWrapper<T>

data class Success<T>(val payload: T?, val headers: Headers) : ResponseWrapper<T>()
data class Failure<T>(@field:Json(name = "code") val code: Int = INVALID_CODE,
                      @field:Json(name = "status") val status: String = "",
                      @field:Json(name = "exception") val exception: String = "",
                      @field:Json(name = "message") val message: String = "",
                      @field:Json(name = "fieldErrors") val fieldErrors: List<FieldError> = emptyList()) : ResponseWrapper<T>()

data class FieldError(@field:Json(name = "field") val field: String,
                      @field:Json(name = "message") val message: String,
                      @field:Json(name = "rejectedValue") val rejectedValue: String)

class NoInternet<T> : ResponseWrapper<T>()
class NoDataFound<T>: ResponseWrapper<T>()

const val INVALID_CODE = -1
const val NO_DATA_FOUND = 404