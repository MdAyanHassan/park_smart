package com.example.firebasesigninwithmanualdi.presentation.parking_spots

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.firebasesigninwithmanualdi.FirebaseSignInApplication
import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.domain.repository.PlacesRepository
import com.google.android.libraries.places.api.model.Place
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ParkingSpotsViewModel(
    private val parkingSpotsRepository: PlacesRepository
): ViewModel() {

    init {
        getAllParkingSpots()
    }

    private val _getAllParkingSpotsResponse =
        MutableStateFlow<Response<List<Place>>>(Response.Loading)

    val getAllParkingSpotsResponse: StateFlow<Response<List<Place>>> = _getAllParkingSpotsResponse.asStateFlow()

    private fun getAllParkingSpots(): Job {
        return viewModelScope.launch {
            parkingSpotsRepository.getParkingSpots().collect {
                _getAllParkingSpotsResponse.value = it
            }
        }
    }

    suspend fun onPaymentSheetInitialize(): Response<List<String>> {
        return parkingSpotsRepository.startPayment()
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as FirebaseSignInApplication
                val appContainer = application.appContainer
                ParkingSpotsViewModel(appContainer.placesRepository)
            }
        }
    }
}