package com.grenzfrequence.githubviewcleanarchitecture.ui.common.data

class NoDataException : Exception()

class NoInternetException : Exception()

data class GenericErrorException(val error: Failure<*>) : Exception(error.toString())
