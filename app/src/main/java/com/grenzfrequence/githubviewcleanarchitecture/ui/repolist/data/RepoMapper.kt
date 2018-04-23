package com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data

import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.dto.ResponseDto
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.NO_DATE
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.NO_ID

fun mapRepo(repoResponseDto: ResponseDto): RepoModel =
        with(repoResponseDto) {
            val repoOwnerModel = repoOwner?.let {
                RepoOwnerModel(
                        it.id,
                        it.login,
                        it.avatarUrl ?: "")
            } ?: RepoOwnerModel(NO_ID, "", "")

            RepoModel(
                    repoOwnerModel,
                    repoId ?: NO_ID,
                    repoName ?: "",
                    repoDescription ?: "",
                    repoUpdatedAt ?: NO_DATE)
        }