package com.example.firebasesigninwithmanualdi.domain.repository

import com.example.firebasesigninwithmanualdi.domain.model.Response

interface ProfileRepository {
    val displayName: String
    val photoUrl: String

    suspend fun signOut(): Response<Boolean>

    suspend fun revokeAccess(): Response<Boolean>
}