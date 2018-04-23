package com.grenzfrequence.githubviewcleanarchitecture.ui

import android.app.Activity
import android.app.Application
import com.grenzfrequence.githubviewcleanarchitecture.ui.di.components.DaggerGitHubViewerAppComponent
import com.grenzfrequence.githubviewcleanarchitecture.ui.di.components.GitHubViewerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class GitHubViewerApp: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    private val gitHubViewerAppComponent: GitHubViewerAppComponent by lazy {
        DaggerGitHubViewerAppComponent
                .builder()
                .application(this)
                .build()
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    override fun onCreate() {
        super.onCreate()
        gitHubViewerAppComponent.inject(this)
    }
}