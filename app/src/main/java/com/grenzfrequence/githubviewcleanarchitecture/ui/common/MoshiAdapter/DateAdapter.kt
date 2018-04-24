package com.grenzfrequence.githubviewcleanarchitecture.ui.common.MoshiAdapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.joda.time.DateTime


class DateAdapter {

    @FromJson
    @JsonDate
    fun fromJson(utcString: String): Long = DateTime(utcString).millis

    @ToJson
    fun toJson(@JsonDate millis: Long): String = DateTime(millis).toString("dd.MM.yyyy")

}