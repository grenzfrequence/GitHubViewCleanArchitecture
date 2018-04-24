package com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data

import org.joda.time.DateTime

data class RepoListModel(val repoList: List<RepoModel>, val maxPageNr: Int)

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
