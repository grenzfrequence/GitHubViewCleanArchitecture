package com.grenzfrequence.githubviewcleanarchitecture.ui.di.modules

import com.grenzfrequence.githubviewcleanarchitecture.BuildConfig
import com.grenzfrequence.githubviewcleanarchitecture.ui.common.MoshiAdapter.DateAdapter
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.CONNECT_TIMEOUT
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.READ_TIMEOUT
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(retrofitBuilder: Retrofit.Builder): Retrofit {
        return retrofitBuilder
                .baseUrl(BuildConfig.BASE_URL)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
            okHttpClient: OkHttpClient,
            rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
            moshiConverterFactory: MoshiConverterFactory
    ): Retrofit.Builder {
        return Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(moshiConverterFactory)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
            okHttpClientBuilder: OkHttpClient.Builder
    ): OkHttpClient {

        return okHttpClientBuilder
                .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClientBuilder(
            httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder {

        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                addInterceptor(httpLoggingInterceptor)
            }
        }
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideRxJavaAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
            Moshi.Builder()
                    .add(DateAdapter())
                    .build()

}
