package com.grenzfrequence.githubviewcleanarchitecture.testdata

import com.grenzfrequence.githubviewcleanarchitecture.ui.common.data.Failure
import com.grenzfrequence.githubviewcleanarchitecture.ui.common.data.Success
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.RepoListModel
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.RepoModel
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.RepoOwnerModel
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.dto.RepoOwnerDto
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.dto.RequestDto
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.data.dto.ResponseDto
import com.grenzfrequence.githubviewcleanarchitecture.ui.repolist.ui.RepoList
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.NO_ID
import com.grenzfrequence.githubviewcleanarchitecture.ui.utils.PAGE_FIRST_POSITION
import okhttp3.Headers
import org.joda.time.DateTime

class GetRepoListTestData {

    companion object {
        val testUser = "Testuser01"
        val firstPageNr = PAGE_FIRST_POSITION
        val nextPageNr = PAGE_FIRST_POSITION + 1
        val outOfBoundsPageNumber = PAGE_FIRST_POSITION + 2
        val maxPageNr = nextPageNr

        val REQUEST = RequestDto(testUser, 1)

        val SUCCESS_RESPONSE_DTO: Success<List<ResponseDto>> = Success(
                arrayListOf(
                        ResponseDto(
                                RepoOwnerDto(1, "TestUser01", "http://www.github.com/test_images/test01.gif"),
                                11,
                                "TestRepo01",
                                "TestDescription01",
                                DateTime(2001, 1, 21, 0, 0, 0, 0).millis
                        ),
                        ResponseDto(
                                RepoOwnerDto(2, "TestUser01", "http://www.github.com/test_images/test02.gif"),
                                22,
                                "TestRepo02",
                                "TestDescription02",
                                DateTime(2002, 2, 22, 0, 0, 0, 0).millis
                        ),
                        ResponseDto(
                                null,
                                null,
                                null,
                                null,
                                0L
                        )
                ),
                Headers
                        .Builder()
                        .add("Field01", "FieldValue01")
                        .add("Field02", "FieldValue02")
                        .build()
        )

        val MAPPED_SUCCESS_RESPONSE = RepoListModel(
                arrayListOf(
                        RepoModel(
                                RepoOwnerModel(1, "TestUser01", "http://www.github.com/test_images/test01.gif"),
                                11,
                                "TestRepo01",
                                "TestDescription01",
                                DateTime(2001, 1, 21, 0, 0, 0, 0).millis
                        ),
                        RepoModel(
                                RepoOwnerModel(2, "TestUser01", "http://www.github.com/test_images/test02.gif"),
                                22,
                                "TestRepo02",
                                "TestDescription02",
                                DateTime(2002, 2, 22, 0, 0, 0, 0).millis
                        ),
                        RepoModel(
                                RepoOwnerModel(NO_ID, "", ""),
                                NO_ID,
                                "",
                                "",
                                0L
                        )
                ),
                0
        )

        val EMPTY_LIST = RepoListModel(arrayListOf(), 0)

        val FAILURE_RESPONSE_DTO: Failure<List<ResponseDto>> = Failure(
                404, "", "DataNotFoundException", "Data not found")

        val FAILURE_SERVER_ERROR: Failure<RepoList> = Failure<RepoList>(code = 500, message = "Server error")
    }
}


