package com.grenzfrequence.githubviewcleanarchitecture.ui.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.grenzfrequence.githubviewcleanarchitecture.ui.GitHubViewerApp
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.PREFERENCES
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GitHubViewerAppModule {

    @Provides
    @Singleton
    fun provideContext(gitHubViewerApp: GitHubViewerApp): Context = gitHubViewerApp.applicationContext

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    @Provides
    fun providesResources(context: Context) = context.resources
}
