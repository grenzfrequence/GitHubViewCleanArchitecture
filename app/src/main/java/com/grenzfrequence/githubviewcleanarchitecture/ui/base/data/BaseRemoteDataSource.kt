package com.grenzfrequence.githubviewcleanarchitecture.ui.base.data

import android.util.Log
import com.grenzfrequence.githubviewcleanarchitecture.ui.common.data.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import java.net.UnknownHostException
import javax.inject.Inject

abstract class BaseRemoteDataSource {

    @Inject
    lateinit var retrofit: Retrofit

    internal fun <T> processApiStream(apiStream: Single<Response<T>>): Single<ResponseWrapper<T>> =
            apiStream
                    .flatMap { currentResponse -> processResponse(response = currentResponse) }
                    .onErrorReturn { error ->
                        when (error) {
                            is UnknownHostException -> NoInternet()
                            else -> {
                                Log.e(TAG, error.localizedMessage)
                                Failure(exception = error.localizedMessage)
                            }
                        }
                    }

    private fun <T> processResponse(response: Response<T>?): Single<ResponseWrapper<T>> =
            response?.let {
                Single.just<ResponseWrapper<T>>(
                        with(response) {
                            when {
                                response.isSuccessful() -> {
                                    Success(payload = response.body(), headers = response.headers())
                                }
                                response.code() == NO_DATA_FOUND -> {
                                    NoDataFound()
                                }
                                else -> {
                                    Log.e(TAG, message())
                                    Failure<T>(code(), "", errorBody().toString(), message())
                                }
                            }
                        })
            } ?: Single.just(NoInternet())

    internal fun <SERVICE> createService(clazz: Class<SERVICE>) = retrofit.create(clazz)
}

val TAG = BaseRemoteDataSource::class.java.simpleName
