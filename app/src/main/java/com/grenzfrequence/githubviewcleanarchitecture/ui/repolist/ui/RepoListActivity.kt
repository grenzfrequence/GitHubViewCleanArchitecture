package com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui

import android.arch.lifecycle.ViewModelProviders
import com.grenzfrequence.githubviewcleanarchitecture.R
import com.grenzfrequence.githubviewcleanarchitecture.ui.base.ui.BaseActivity
import com.grenzfrequence.githubviewcleanarchitecture.ui.base.ui.BaseViewModel

class RepoListActivity : BaseActivity() {

    private val repoListViewModel by lazy { injectViewModel() as RepoListActivityViewModel }

    override fun getLayoutId(): Int = R.layout.repo_list_activity

    @Suppress("UNCHECKED_CAST")
    protected fun injectViewModel(): BaseViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(RepoListActivityViewModel::class.java)
}