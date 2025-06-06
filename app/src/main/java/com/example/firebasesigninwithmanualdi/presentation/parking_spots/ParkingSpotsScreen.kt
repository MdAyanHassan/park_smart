package com.example.firebasesigninwithmanualdi.presentation.parking_spots

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasesigninwithmanualdi.components.ProgressBar
import com.example.firebasesigninwithmanualdi.core.Utils
import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.presentation.parking_spots.components.ParkingSpotTopBar
import com.example.firebasesigninwithmanualdi.presentation.parking_spots.components.ParkingSpotsList
import kotlin.Unit

@Composable
fun ParkingSpotScreen(
    parkingSpotsViewModel: ParkingSpotsViewModel = viewModel(factory = ParkingSpotsViewModel.Companion.Factory),
    onBackClick: () -> Unit,
    onNavigationClick: (String?, String?) -> Unit
) {
    val getAllParkingSpotsResponse by parkingSpotsViewModel.getAllParkingSpotsResponse.collectAsState()

    Scaffold(
        topBar = {
            ParkingSpotTopBar(
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->

        when (getAllParkingSpotsResponse) {
            is Response.Failure -> {
                LaunchedEffect(key1 = Unit) {
                    Utils.printError((getAllParkingSpotsResponse as Response.Failure).e)
                }
            }
            is Response.Loading -> {
                ProgressBar()
            }
            is Response.Success -> {
                (getAllParkingSpotsResponse as Response.Success).data?.let {
                    ParkingSpotsList(
                        parkingSpots = it,
                        onPayClicked = {

                        },
                        onNavigateClicked = {
                            onNavigationClick(it.id, it.displayName)
                        },
                        paddingValues = paddingValues
                    )
                }
            }
        }

    }
}