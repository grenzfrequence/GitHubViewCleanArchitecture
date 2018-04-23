package com.grenzfrequence.githubviewcleanarchitecture.ui.common.MoshiAdapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.joda.time.DateTime


class DateAdapter {

    @FromJson
    @JsonDate
    fun fromJson(utcString: String): DateTime = DateTime(utcString)

    @ToJson
    fun toJson(@JsonDate date: DateTime): String = date.toString("dd.MM.yyyy")

}