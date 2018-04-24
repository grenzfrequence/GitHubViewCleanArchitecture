package com.grenzfrequence.githubviewcleanarchitecture.testcases.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.grenzfrequence.githubviewcleanarchitecture.testdata.GetRepoListTestData
import com.grenzfrequence.githubviewcleanarchitecture.ui.common.data.GenericErrorException
import com.grenzfrequence.githubviewcleanarchitecture.ui.common.data.NoDataException
import com.grenzfrequence.githubviewcleanarchitecture.ui.common.data.NoInternetException
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.GetRepoListUseCase
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui.LoadingStatus
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui.RepoListFragmentViewModel
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui.RepoListUiModel
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.UiErrorState
import com.grenzfrequence.githubviewcleanarchitecture.utils.RxSchedulersOverride
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RepoListFragmentViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var rxSchedulersOverride = RxSchedulersOverride()

    val getRepoListUseCase: GetRepoListUseCase = mock()
    val repoListUiModelObserver: Observer<RepoListUiModel> = mock()

    lateinit var repoListFragmentViewModel: RepoListFragmentViewModel

    @Before
    fun setup() {
        repoListFragmentViewModel = RepoListFragmentViewModel(getRepoListUseCase, GetRepoListTestData.maxPageNr)
        repoListFragmentViewModel.repoListUiModel.observeForever(repoListUiModelObserver)
    }

    @Test
    fun shouldLoadFirstPageAndNotify() {
        givenFirstPageList()
        repoListFragmentViewModel.onLoadNextPage(GetRepoListTestData.firstPageNr)
        verify(repoListUiModelObserver).onChanged(RepoListUiModel(LoadingStatus.FirstPageLoading))
        verify(repoListUiModelObserver).onChanged(RepoListUiModel(LoadingStatus.SuccessLoading(
                GetRepoListTestData.MAPPED_SUCCESS_RESPONSE.repoList
        )))
    }

    @Test
    fun shouldLoadNextPageAndNotify() {
        givenNextPageList()
        repoListFragmentViewModel.onLoadNextPage(GetRepoListTestData.nextPageNr)
        verify(repoListUiModelObserver).onChanged(RepoListUiModel(LoadingStatus.NextPageLoading))
        verify(repoListUiModelObserver).onChanged(RepoListUiModel(LoadingStatus.SuccessLoading(
                GetRepoListTestData.MAPPED_SUCCESS_RESPONSE.repoList
        )))
    }

    @Test
    fun shouldLoadPageAnyMoreAndNoNotification() {
        givenNextPageList()
        repoListFragmentViewModel.onLoadNextPage(GetRepoListTestData.outOfBoundsPageNumber)
        verify(repoListUiModelObserver, never()).onChanged(RepoListUiModel(LoadingStatus.NextPageLoading))
        verify(repoListUiModelObserver, never()).onChanged(RepoListUiModel(LoadingStatus.SuccessLoading(
                GetRepoListTestData.MAPPED_SUCCESS_RESPONSE.repoList
        )))
        verify(repoListUiModelObserver, never()).onChanged(RepoListUiModel(LoadingStatus.FailedLoading(
                UiErrorState.NoData()
        )))
        verify(repoListUiModelObserver, never()).onChanged(RepoListUiModel(LoadingStatus.FailedLoading(
                UiErrorState.NoInternet()
        )))
        verify(repoListUiModelObserver, never()).onChanged(RepoListUiModel(LoadingStatus.FailedLoading(
                UiErrorState.General()
        )))
    }

    @Test
    fun shouldNotifyNoDataBecauseNotFound() {
        givenNoDataException()
        repoListFragmentViewModel.onLoadNextPage(GetRepoListTestData.firstPageNr)
        verify(repoListUiModelObserver).onChanged(RepoListUiModel(LoadingStatus.FailedLoading(
                UiErrorState.NoData()
        )))
    }

    @Test
    fun shouldNotifyNoDataBecauseEmptyList() {
        givenEmptyList()
        repoListFragmentViewModel.onLoadNextPage(GetRepoListTestData.firstPageNr)
        verify(repoListUiModelObserver).onChanged(RepoListUiModel(LoadingStatus.FailedLoading(
                UiErrorState.NoData()
        )))
    }

    @Test
    fun shouldNotifyNoInternet() {
        givenNoInternetException()
        repoListFragmentViewModel.onLoadNextPage(GetRepoListTestData.firstPageNr)
        verify(repoListUiModelObserver).onChanged(RepoListUiModel(LoadingStatus.FailedLoading(
                UiErrorState.NoInternet()
        )))
    }

    @Test
    fun shouldNotifyGeneralError() {
        givenGeneralError()
        repoListFragmentViewModel.onLoadNextPage(GetRepoListTestData.firstPageNr)
        verify(repoListUiModelObserver).onChanged(RepoListUiModel(LoadingStatus.FailedLoading(
                UiErrorState.General()
        )))
    }

    private fun givenFirstPageList() {
        whenever(getRepoListUseCase.getRepoList(any(), any(), any(), any()))
                .thenReturn(Single.just(GetRepoListTestData.MAPPED_SUCCESS_RESPONSE))
    }

    private fun givenNextPageList() {
        whenever(getRepoListUseCase.getRepoList(any(), any(), any(), any()))
                .thenReturn(Single.just(GetRepoListTestData.MAPPED_SUCCESS_RESPONSE))
    }

    private fun givenNoDataException() {
        val thenReturn = whenever(getRepoListUseCase.getRepoList(any(), any(), any(), any()))
                .thenReturn(Single.error(NoDataException()))
    }

    private fun givenEmptyList() {
        whenever(getRepoListUseCase.getRepoList(any(), any(), any(), any()))
                .thenReturn(Single.just(GetRepoListTestData.EMPTY_LIST))
    }

    private fun givenNoInternetException() {
        whenever(getRepoListUseCase.getRepoList(any(), any(), any(), any()))
                .thenReturn(Single.error(NoInternetException()))
    }

    private fun givenGeneralError() {
        whenever(getRepoListUseCase.getRepoList(any(), any(), any(), any()))
                .thenReturn(Single.error(GenericErrorException(
                        GetRepoListTestData.FAILURE_SERVER_ERROR)))
    }
}