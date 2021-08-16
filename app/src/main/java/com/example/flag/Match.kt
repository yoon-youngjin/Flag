package com.example.flag

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Match(
    val id: String? = null,
    val date: String? = null,
    val time: String? = null,
    val event: String? = null,
    val group: String? = null,
    val homeUid: String? = null,
    val awayUid: String? = null
) {
}