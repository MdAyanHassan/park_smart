package com.example.firebasesigninwithmanualdi.domain.repository

import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.google.android.libraries.places.api.model.Place
import kotlinx.coroutines.flow.Flow

interface PlacesRepository {
    suspend fun getParkingSpots(): Flow<Response<List<Place>>>
}