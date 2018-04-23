package com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.repository

import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.dto.ResponseDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetRepoListApiService {

    // http://api.github.com/users/{user}/repos?page=2&per_page=2
    @GET("users/{user}/repos")
    fun getRepoList(
            @Path("user") userName: String,
            @Query("page") pageNr: Int,
            @Query("per_page") itemsPerPage: Int,
            @Query("sort") sortField: String): Single<Response<List<ResponseDto>>>
}