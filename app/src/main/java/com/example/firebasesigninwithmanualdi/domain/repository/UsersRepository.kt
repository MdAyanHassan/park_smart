package com.example.firebasesigninwithmanualdi.domain.repository

import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getAllUsersInfo(): Flow<Response<List<User>>>

}