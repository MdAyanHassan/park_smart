package com.example.firebasesigninwithmanualdi.domain.repository

import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential

interface AuthenticationRepository {

    val isUserSignedIn: Boolean

    suspend fun oneTapSignInWithGoogle(): Response<BeginSignInResult>

    suspend fun signInWithGoogle(googleAuthCredentials: AuthCredential): Response<Boolean>

}