package com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui

import android.arch.lifecycle.MutableLiveData
import com.grenzfrequence.githubviewcleanarchitecture.ui.base.ui.BaseViewModel
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.GetRepoListUseCase
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.RepoListModel
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.Extensions.disposeSafety
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.PAGE_FIRST_POSITION
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.TIME_AFTER_DATA_ENTRY
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.UiErrorState
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.exceptionMapper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoListFragmentViewModel @Inject constructor(val getRepoListUseCase: GetRepoListUseCase) : BaseViewModel() {

    // Parameters
    var userName: String = ""

    // uiData
    val repoListUiModel = MutableLiveData<RepoListUiModel>()

    // Loaded Data
    private var repoList = ArrayList<RepoModel>()
    private var maxPageNumber: Int = 0

    // disposables
    // I use no compositedisposables because dispose does not work on it
    private var timerDisposable: Disposable? = null
    private var fetchDataDisposable: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        timerDisposable?.disposeSafety()
        fetchDataDisposable?.disposeSafety()
    }

    fun onLoadNextPage(pageNr: Int) {
        when {
            pageNr == PAGE_FIRST_POSITION -> {
                repoList.clear()
                notifyFirstPageLoading()
            }
            pageNr <= maxPageNumber -> {
                notifyNextPageLoading()
            }
            else -> {
                return
            }
        }
        fetchDataDisposable?.disposeSafety()
        fetchDataDisposable = getRepoListUseCase.getRepoList(userName, pageNr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { repoListModel: RepoListModel ->
                            maxPageNumber = repoListModel.maxPageNr
                            repoList.addAll(repoListModel.repoList)

                            when {
                                !repoListModel.repoList.isEmpty() -> {
                                    notifySuccessLoading(repoList)
                                }
                                else -> {
                                    notifyFailedLoading(UiErrorState.NoData())
                                }
                            }
                        },
                        { error ->
                            exceptionMapper(error) { uiErrorState: UiErrorState ->
                                notifyFailedLoading(uiErrorState)
                            }
                        }
                )
    }

    fun onUserNameChanged(userName: String) {
        this.userName = userName
        timerDisposable?.disposeSafety()
        timerDisposable = Observable
                .timer(TIME_AFTER_DATA_ENTRY, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ time: Long -> onLoadNextPage(PAGE_FIRST_POSITION) })
    }

    private fun notifyFirstPageLoading() {
        repoListUiModel.value = RepoListUiModel(LoadingStatus.FirstPageLoading)
    }

    private fun notifyNextPageLoading() {
        repoListUiModel.value = RepoListUiModel(LoadingStatus.NextPageLoading)
    }

    private fun notifySuccessLoading(repoList: RepoList) {
        repoListUiModel.value = RepoListUiModel(LoadingStatus.SuccessLoading(repoList))
    }

    private fun notifyFailedLoading(errorState: UiErrorState) {
        repoListUiModel.value = RepoListUiModel(LoadingStatus.FailedLoading(errorState))
    }
}
