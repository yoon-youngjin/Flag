package com.example.flag

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val username: String? = null,
    val email: String? = null,
    val school: String? = null,
    val team: String? = null,
    val record: String? = null
) {
}