package com.grenzfrequence.githubviewcleanarchitecture.ui.di.components

import com.grenzfrequence.githubviewcleanarchitecture.ui.GitHubViewerApp
import com.grenzfrequence.githubviewcleanarchitecture.ui.common.ui.ViewModelFactory
import com.grenzfrequence.githubviewcleanarchitecture.ui.di.modules.BuildersModule
import com.grenzfrequence.githubviewcleanarchitecture.ui.di.modules.GitHubViewerAppModule
import com.grenzfrequence.githubviewcleanarchitecture.ui.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        GitHubViewerAppModule::class,
        NetworkModule::class,
        BuildersModule::class
))
interface GitHubViewerAppComponent {
    fun inject(gitHubViewerApp: GitHubViewerApp)

    fun inject(viewModelFactory: ViewModelFactory)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: GitHubViewerApp): Builder

        fun build(): GitHubViewerAppComponent
    }
}