package com.example.firebasesigninwithmanualdi.data.repository

import com.example.firebasesigninwithmanualdi.core.CREATED_AT
import com.example.firebasesigninwithmanualdi.core.USERS_COLLECTION
import com.example.firebasesigninwithmanualdi.core.Utils
import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.domain.model.User
import com.example.firebasesigninwithmanualdi.domain.repository.ProfileRepository
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ProfileRepositoryImpl(
    private val auth: FirebaseAuth,
    private val oneTapClient: SignInClient,
    private val signInClient: GoogleSignInClient,
    private val db: FirebaseFirestore
): ProfileRepository {

    override val displayName: String
        get() = auth.currentUser?.displayName.toString()

    override val photoUrl: String
        get() = auth.currentUser?.photoUrl.toString()

    override suspend fun signOut(): Response<Boolean> {
        return try {
            oneTapClient.signOut().await()
            auth.signOut()
            Response.Success(true)
        } catch (e: Exception) {
            Utils.printError(e)
            if (e is CancellationException) throw e
            Response.Failure(e)
        }
    }
    override suspend fun revokeAccess(): Response<Boolean> {
        return try {
            auth.currentUser?.let {
                db.collection(USERS_COLLECTION).document(it.uid).delete().await()
                signInClient.revokeAccess().await()
                oneTapClient.signOut().await()
                it.delete().await()
            }
            Response.Success(true)
        } catch (e: Exception) {
            Utils.printError(e)
            if (e is CancellationException) throw e
            Response.Failure(e)
        }
    }
}