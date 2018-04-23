package com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.repository

import com.grenzfrequence.githubviewcleanarchitecture.ui.base.data.BaseRemoteDataSource
import com.grenzfrequence.githubviewcleanarchitecture.ui.common.data.ResponseWrapper
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.dto.RequestDto
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.dto.ResponseDto
import io.reactivex.Single
import javax.inject.Inject

class GetRepoListRepository @Inject constructor(private val remoteDataSource: Remote) {

    fun getRepoList(repoListRequest: RequestDto) = remoteDataSource.getRepoList(repoListRequest)

    class Remote @Inject constructor() : BaseRemoteDataSource() {

        fun getRepoList(repoListRequest: RequestDto): Single<ResponseWrapper<List<ResponseDto>>> {
            val repoListApiService = createService(GetRepoListApiService::class.java)
            val apiStream = with(repoListRequest) {
                repoListApiService.getRepoList(userName, pageNr, itemsPerPage, sort)
            }
            return processApiStream(apiStream)
        }
    }
}
