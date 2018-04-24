package com.grenzfrequence.githubviewcleanarchitecture.testcases.mappers

import com.grenzfrequence.githubviewcleanarchitecture.testdata.GetRepoListTestData
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.mapRepo
import org.junit.Assert.assertEquals
import org.junit.Test

class RepoListMapperTests {

    @Test
    fun shouldMapRepoList() {
        val repoModelList = GetRepoListTestData.SUCCESS_RESPONSE_DTO.payload?.map {
            mapRepo(it)
        }
        repoModelList?.forEachIndexed { index, repoModel ->
            assertEquals(GetRepoListTestData.MAPPED_SUCCESS_RESPONSE.repoList[index], repoModel)
        }
    }
}