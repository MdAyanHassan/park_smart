package com.example.firebasesigninwithmanualdi.presentation.parking_spots.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.libraries.places.api.model.Place
import kotlin.Unit

@Composable
fun ParkingSpotsList(
    parkingSpots: List<Place>,
    onPayClicked: (Place) -> Unit,
    onNavigateClicked: (Place) -> Unit,
    paddingValues: PaddingValues
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(parkingSpots) { parkingSpot ->
            ParkingSpotItem(
                parkingSpot = parkingSpot,
                onPayClicked = {
                    onPayClicked(parkingSpot)
                },
                onNavigateClicked = {
                    onNavigateClicked(parkingSpot)
                }
            )
        }
    }
}