package com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data

import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.dto.RequestDto
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.dto.ResponseDto
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.repository.GetRepoListRepository
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.GitHubHeaderAnalyzer
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.QUERY_MAX_ITEMS_PER_PAGE
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.QUERY_UPDATED_SORT
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.responseMapper
import io.reactivex.Single
import javax.inject.Inject

class GetRepoListUseCase @Inject constructor(
        private val getRepoListRepository: GetRepoListRepository,
        private val gitHub: GitHubHeaderAnalyzer) {

    fun getRepoList(
            userName: String, pageNr: Int,
            itemsPerPage: Int = QUERY_MAX_ITEMS_PER_PAGE,
            sort: String = QUERY_UPDATED_SORT): Single<RepoListModel> =

            getRepoListRepository.getRepoList(RequestDto(userName, pageNr))
                    .flatMap { response ->
                        var result = Single.just(RepoListModel(ArrayList(), 0))
                        responseMapper(response, { exception -> result = Single.error(exception) }) { payload, headers ->
                            result = Single.just(
                                    RepoListModel(
                                            payload.map { responseDto: ResponseDto ->
                                                mapRepo(responseDto)
                                            },
                                            gitHub.getLastPageNr(headers)
                                    ))
                        }
                        result
                    }
}