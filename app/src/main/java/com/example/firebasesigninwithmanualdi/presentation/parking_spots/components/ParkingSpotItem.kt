package com.example.firebasesigninwithmanualdi.presentation.parking_spots.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firebasesigninwithmanualdi.ui.theme.FirebaseSignInWithManualDITheme
import com.google.android.libraries.places.api.model.Place
import kotlin.Unit

@Composable
fun ParkingSpotItem(
    parkingSpot: Place,
    onPayClicked: () -> Unit,
    onNavigateClicked: () -> Unit
) {

    ParkingSpotListItem(
        parkingSpotName = parkingSpot.displayName ?: "Unknown Spot",
        parkingSpotAddress = parkingSpot.formattedAddress ?: "No address",
        onPayClicked = onPayClicked,
        onNavigateClicked = onNavigateClicked
    )
}
@OptIn(ExperimentalMaterial3Api::class) // For Card click
@Composable
fun ParkingSpotListItem(
    parkingSpotName: String,
    parkingSpotAddress: String,
    onPayClicked: () -> Unit,
    onNavigateClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        // onClick = { /* Optional: if the whole card should be clickable for details */ }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Parking Spot Info
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Place,
                    contentDescription = "Parking Spot",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = parkingSpotName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = parkingSpotAddress,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 36.dp) // Align with title text if icon is present
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Aligns buttons to the right
            ) {
                OutlinedButton( // Or use TextButton for less emphasis
                    onClick = onNavigateClicked,
                    modifier = Modifier.weight(1f) // Give equal weight if you want them to fill space
                    // Or remove weight for wrap_content behavior
                ) {
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = "Navigate",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Navigate")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button( // Filled button for primary action
                    onClick = onPayClicked,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Filled.ShoppingCart,
                        contentDescription = "Pay Now",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Pay Now")
                }
            }
        }
    }
}

// Dummy data for preview
object PreviewData {
    val samplePlace: Place = Place.builder()
        .setName("City Center Parking Garage")
        .setAddress("123 Main St, Anytown, USA")
        // Add other fields if your ParkingSpotItem uses them directly from Place object
        .build()
}

@Preview(showBackground = true)
@Composable
fun ParkingSpotListItemPreview() {
    FirebaseSignInWithManualDITheme { // Wrap with your app's theme for accurate preview
        ParkingSpotListItem(
            parkingSpotName = PreviewData.samplePlace.name ?: "Unknown Spot",
            parkingSpotAddress = PreviewData.samplePlace.address ?: "No address",
            onPayClicked = { },
            onNavigateClicked = { }
        )
    }
}