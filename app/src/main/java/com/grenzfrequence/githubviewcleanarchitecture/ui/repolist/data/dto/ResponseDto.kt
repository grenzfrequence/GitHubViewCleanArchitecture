package com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.dto

import com.grenzfrequence.githubviewcleanarchitecture.ui.common.MoshiAdapter.JsonDate
import com.squareup.moshi.Json
import org.joda.time.DateTime

data class ResponseDto(
        @field:Json(name = "owner") val repoOwner: RepoOwnerDto?,
        @field:Json(name = "id") val repoId: Int?,
        @field:Json(name = "name") val repoName: String?,
        @field:Json(name = "description") val repoDescription: String?,
        @field:Json(name = "updated_at") @field:JsonDate val repoUpdatedAt: DateTime?
)

data class RepoOwnerDto(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "login") val login: String,
        @field:Json(name = "avatar_url") val avatarUrl: String?
)