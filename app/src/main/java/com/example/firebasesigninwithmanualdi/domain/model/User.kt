package com.example.firebasesigninwithmanualdi.domain.model

import java.util.Date

data class User(
    val display_name: String? = null,

    val photo_url: String? = null,

    val email: String? = null,

    val created_at: Date? = null
)
