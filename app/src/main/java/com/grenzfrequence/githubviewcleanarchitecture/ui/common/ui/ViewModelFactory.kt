package com.grenzfrequence.githubviewcleanarchitecture.ui.common.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.GetRepoListUseCase
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui.RepoListActivityViewModel
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui.RepoListFragmentViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(): ViewModelProvider.NewInstanceFactory() {

    @Inject
    lateinit var getRepoListUseCase: GetRepoListUseCase

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(RepoListActivityViewModel::class.java) ->
                    RepoListActivityViewModel()
                isAssignableFrom(RepoListFragmentViewModel::class.java) ->
                    RepoListFragmentViewModel(getRepoListUseCase)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}