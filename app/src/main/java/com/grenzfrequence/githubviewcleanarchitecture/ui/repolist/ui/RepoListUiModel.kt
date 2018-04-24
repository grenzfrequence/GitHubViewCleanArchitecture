package com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui

import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.RepoModel
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.UiErrorState

data class RepoListUiModel(val loadingStatus: LoadingStatus)

sealed class LoadingStatus {
    object FirstPageLoading : LoadingStatus()
    object NextPageLoading : LoadingStatus()
    class SuccessLoading(val data: RepoList) : LoadingStatus()
    class FailedLoading(val errorState: UiErrorState) : LoadingStatus()
}

typealias RepoList = List<RepoModel>
