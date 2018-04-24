package com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data

import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui.RepoList

data class RepoListModel(val repoList: RepoList, val maxPageNr: Int)

data class RepoModel(
        val repoOwner: RepoOwnerModel,
        val repoId: Int,
        val repoName: String,
        val repoDescription: String = "",
        val repoUpdatedAt: Long)

data class RepoOwnerModel(
        val ownerId: Int,
        val login: String,
        val avatarUrl: String)
