package com.example.firebasesigninwithmanualdi.data.repository

import android.util.Log
import com.example.firebasesigninwithmanualdi.core.CREATED_AT
import com.example.firebasesigninwithmanualdi.core.DEBUG_TAG
import com.example.firebasesigninwithmanualdi.core.USERS_COLLECTION
import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.domain.model.User
import com.example.firebasesigninwithmanualdi.domain.repository.UsersRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UsersRepositoryImpl(
    private val db: FirebaseFirestore
): UsersRepository {

    override suspend fun getAllUsersInfo(): Flow<Response<List<User>>> {
        return callbackFlow {
            val snapshotListener = db.collection(USERS_COLLECTION)
                .orderBy(CREATED_AT)
                .addSnapshotListener { snapshot, error ->
                    val usersResponse = if (snapshot != null) {
                        val users = snapshot.toObjects(User::class.java)
                        Log.d(DEBUG_TAG, "$users")
                        Response.Success(users)
                    }
                    else {
                        Response.Failure(error)
                    }
                    this.trySend(usersResponse)
                }
            this.awaitClose {
                snapshotListener.remove()
            }
        }
    }

}