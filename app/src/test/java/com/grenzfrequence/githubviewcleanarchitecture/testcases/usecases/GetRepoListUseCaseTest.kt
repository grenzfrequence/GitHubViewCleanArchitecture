package com.grenzfrequence.githubviewcleanarchitecture.testcases.usecases

import com.grenzfrequence.githubviewcleanarchitecture.testdata.GetRepoListTestData
import com.grenzfrequence.githubviewcleanarchitecture.ui.common.data.*
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.GetRepoListUseCase
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.repository.GetRepoListRepository
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.GitHubHeaderAnalyzer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Test

class GetRepoListUseCaseTest {

    private val getRepoListRepository: GetRepoListRepository = mock()
    private val gitHubHeaderAnalyzer: GitHubHeaderAnalyzer = mock()
    val getRepoListUseCaseTest = GetRepoListUseCase(getRepoListRepository, gitHubHeaderAnalyzer)

    @Test
    fun shouldHasCorrectRepoListAfterMapping() {
        givenSuccessResponse()
        getRepoListUseCaseTest
                .getRepoList(GetRepoListTestData.testUser, GetRepoListTestData.firstPageNr)
                .test()
                .assertResult(GetRepoListTestData.MAPPED_SUCCESS_RESPONSE)
    }

    @Test()
    fun shouldThrowNoDataException() {
        givenNoDataResponse()
        getRepoListUseCaseTest
                .getRepoList(GetRepoListTestData.testUser, GetRepoListTestData.firstPageNr)
                .test()
                .assertError(NoDataException::class.java)
    }

    @Test
    fun shouldThrowNoInternetException() {
        givenNoInternetResponse()
        getRepoListUseCaseTest
                .getRepoList(GetRepoListTestData.testUser, GetRepoListTestData.firstPageNr)
                .test()
                .assertError(NoInternetException::class.java)
    }

    @Test
    fun shouldThrowGenericErrorException() {
        givenFailureResponse()
        getRepoListUseCaseTest
                .getRepoList(GetRepoListTestData.testUser, GetRepoListTestData.firstPageNr)
                .test()
                .assertError(GenericErrorException::class.java)
    }

    private fun givenSuccessResponse() {
        whenever(getRepoListRepository.getRepoList(any()))
                .thenReturn(Single.just(GetRepoListTestData.SUCCESS_RESPONSE_DTO))
    }

    private fun givenNoDataResponse() {
        whenever(getRepoListRepository.getRepoList(any()))
                .thenReturn(Single.just(NoDataFound()))
    }

    private fun givenNoInternetResponse() {
        whenever(getRepoListRepository.getRepoList(any()))
                .thenReturn(Single.just(NoInternet()))
    }

    private fun givenFailureResponse() {
        whenever(getRepoListRepository.getRepoList(any()))
                .thenReturn(Single.just(Failure(code = 500)))
    }
}