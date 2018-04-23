package com.grenzfrequence.githubviewcleanarchitecture.ui.di.modules

import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui.RepoListActivity
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui.RepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [(ActivitiesModule::class)])
    abstract fun bindRepoListActivity(): RepoListActivity

    @ContributesAndroidInjector
    abstract fun bindRepoListFragment(): RepoListFragment
}