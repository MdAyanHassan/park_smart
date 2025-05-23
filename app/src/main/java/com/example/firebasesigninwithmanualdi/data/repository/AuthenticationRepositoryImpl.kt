package com.example.firebasesigninwithmanualdi.data.repository

import com.example.firebasesigninwithmanualdi.core.CREATED_AT
import com.example.firebasesigninwithmanualdi.core.DISPLAY_NAME
import com.example.firebasesigninwithmanualdi.core.EMAIL
import com.example.firebasesigninwithmanualdi.core.PHOTO_URL
import com.example.firebasesigninwithmanualdi.core.USERS_COLLECTION
import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.domain.repository.AuthenticationRepository
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class AuthenticationRepositoryImpl(
    private val auth: FirebaseAuth,
    private val oneTapClient: SignInClient,
    private val beginSignInRequest: BeginSignInRequest,
    private val db: FirebaseFirestore
): AuthenticationRepository {

    override val isUserSignedIn: Boolean
        get() = auth.currentUser != null

    override suspend fun oneTapSignInWithGoogle(): Response<BeginSignInResult> {
        return try {
            val signInResult = oneTapClient.beginSignIn(beginSignInRequest).await()

            Response.Success(signInResult)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Response.Failure(e)
        }
    }

    override suspend fun signInWithGoogle(googleAuthCredentials: AuthCredential): Response<Boolean> {
        return try {
            val authResult = auth.signInWithCredential(googleAuthCredentials).await()

            if (authResult.additionalUserInfo?.isNewUser == true) {
                auth.currentUser?.let {
                    val user = it.toUser()
                    db.collection(USERS_COLLECTION).document(it.uid).set(user).await()
                }
            }

            Response.Success(true)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Response.Failure(e)
        }
    }

}

private fun FirebaseUser.toUser() = mapOf(
    DISPLAY_NAME to this.displayName,
    EMAIL to this.email,
    PHOTO_URL to this.photoUrl,
    CREATED_AT to FieldValue.serverTimestamp()
)