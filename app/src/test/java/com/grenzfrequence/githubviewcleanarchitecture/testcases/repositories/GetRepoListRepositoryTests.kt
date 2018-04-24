package com.grenzfrequence.githubviewcleanarchitecture.testcases.repositories

import com.grenzfrequence.githubviewcleanarchitecture.testdata.GetRepoListTestData
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.repository.GetRepoListRepository
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.repository.Remote
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Test

class GetRepoListRepositoryTests {

    private var remoteDataSource: Remote = mock()
    private var getRepoListRepository = GetRepoListRepository(remoteDataSource)

    @Test
    fun shouldFetchSuccessData() {
        givenSuccessResponse()
        getRepoListRepository
                .getRepoList(GetRepoListTestData.REQUEST)
                .test()
                .apply {
                    assertNoErrors()
                    assertResult(GetRepoListTestData.SUCCESS_RESPONSE_DTO)
                }
    }

    @Test
    fun shouldFindNoData() {
        givenFailureResponse()
        getRepoListRepository
                .getRepoList(GetRepoListTestData.REQUEST)
                .test()
                .apply {
                    assertResult(GetRepoListTestData.FAILURE_RESPONSE_DTO)
                }
    }

    private fun givenSuccessResponse() {
        whenever(remoteDataSource.getRepoList(any())).thenReturn(
                Single.just(GetRepoListTestData.SUCCESS_RESPONSE_DTO)
        )
    }

    private fun givenFailureResponse() {
        whenever(remoteDataSource.getRepoList(any())).thenReturn(
                Single.just(GetRepoListTestData.FAILURE_RESPONSE_DTO)
        )
    }
}