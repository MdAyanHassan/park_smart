package com.example.firebasesigninwithmanualdi.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.firebasesigninwithmanualdi.core.TAG
import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.domain.repository.PlacesRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.libraries.places.api.model.CircularBounds
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.SearchNearbyRequest
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.functions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PlacesRepositoryImpl(
    private val placesClient: PlacesClient,
    private val fusedLocationClient: FusedLocationProviderClient
): PlacesRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getParkingSpots(): Flow<Response<List<Place>>> {
        return callbackFlow {
            val cancellationTokenSource = CancellationTokenSource()

            launch {
                try {
                    val priority = Priority.PRIORITY_HIGH_ACCURACY
                    val currentLocation = fusedLocationClient.getCurrentLocation(
                        priority,
                        cancellationTokenSource.token
                    ).await()
                    val parkingSpotsResponse = if (currentLocation == null) {
                        Response.Failure(Exception("No location found"))
                    } else {
                        val placeFields =
                            listOf(Place.Field.ID, Place.Field.DISPLAY_NAME, Place.Field.ADDRESS)

                        val includedTypes = listOf("parking")

                        val centre = LatLng(currentLocation.latitude, currentLocation.longitude)

                        val circle = CircularBounds.newInstance(centre, 3000.0)

                        val searchNearbyRequest = SearchNearbyRequest
                            .builder(
                                circle,
                                placeFields
                            )
                            .setIncludedTypes(includedTypes)
                            .setMaxResultCount(10)
                            .build()

                        val placesResponse = placesClient.searchNearby(searchNearbyRequest)
                            .await()

                        val placesList = placesResponse.places

                        Response.Success(placesList)
                    }
                    trySend(parkingSpotsResponse)
                } catch (e: SecurityException) {
                    trySend(Response.Failure(e))
                } catch (e: Exception) {
                    trySend(Response.Failure(e))
                } finally {
                    channel.close()
                }

            }
            awaitClose {
                cancellationTokenSource.cancel()
            }
        }
    }

    override suspend fun startPayment(): Response<List<String>> {
        val functions = Firebase.functions

        try {
            val data = hashMapOf("amount" to 5000, "currency" to "inr")
            Log.d(TAG, "startPayment: $data")
            val currUser = FirebaseAuth.getInstance().currentUser
            Log.d(TAG, "startPayment: $currUser")
            if (currUser != null) {
                val result = functions
                    .getHttpsCallable("paymentSheet")
                    .call(data)
                    .await()

                Log.d(TAG, "Result: $result")

                if (result.data == null) {
                    Log.d(TAG, "Result data is null")
                    return Response.Failure(Exception("No data found"))
                }
                else {
                    Log.d(TAG, "Result data: ${result.data}")
                    val resultData = result.data as Map<*, *>

                    val clientSecret = resultData["paymentIntent"] as String
                    val publishableKey = resultData["publishableKey"] as String

                    return Response.Success(listOf(clientSecret, publishableKey))
                }
            }
            else {
                return Response.Failure(Exception("No user found"))
            }
        } catch (e: Exception) {
            Log.d(TAG, "startPayment: $e")
            return Response.Failure(e)
        }
    }
}