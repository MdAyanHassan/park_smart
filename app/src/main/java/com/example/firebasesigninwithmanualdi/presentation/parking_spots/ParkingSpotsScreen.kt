package com.example.firebasesigninwithmanualdi.presentation.parking_spots

import PaymentCancelledInfoScreen
import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasesigninwithmanualdi.components.ProgressBar
import com.example.firebasesigninwithmanualdi.core.TAG
import com.example.firebasesigninwithmanualdi.core.Utils
import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.presentation.parking_spots.components.ParkingSpotTopBar
import com.example.firebasesigninwithmanualdi.presentation.parking_spots.components.ParkingSpotsList
import com.example.firebasesigninwithmanualdi.presentation.parking_spots.components.PaymentFailScreen
import com.example.firebasesigninwithmanualdi.presentation.parking_spots.components.PaymentSuccessScreen
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult

@Composable
fun ParkingSpotScreen(
    parkingSpotsViewModel: ParkingSpotsViewModel = viewModel(factory = ParkingSpotsViewModel.Companion.Factory),
    onBackClick: () -> Unit,
    onNavigationClick: (String?, String?) -> Unit
) {
    var paymentOutcomeState by remember { mutableIntStateOf(0) }

    val onPaymentSheetResult: (PaymentSheetResult) -> Unit = { paymentSheetResult ->
        paymentOutcomeState = when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                // Payment was cancelled
                1
            }

            is PaymentSheetResult.Failed -> {
                // Payment failed
                2
            }

            is PaymentSheetResult.Completed -> {
                // Payment completed
                3
            }
        }
    }
    val paymentSheet = remember { PaymentSheet.Builder(onPaymentSheetResult) }.build()
    val context = LocalContext.current
    var paymentIntentClientSecret by remember { mutableStateOf<String?>(null) }

    val getAllParkingSpotsResponse by parkingSpotsViewModel.getAllParkingSpotsResponse.collectAsState()

    LaunchedEffect(context) {
        val response = parkingSpotsViewModel.onPaymentSheetInitialize()
        Log.d(TAG, "ParkingSpotScreen: $response")
        if (response is Response.Success) {
            val publishableKey = response.data?.get(1)!!
            paymentIntentClientSecret = response.data[0]

            PaymentConfiguration.init(context, publishableKey)
        }
    }

    when (paymentOutcomeState) {
        0 -> {
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
                                    val currentClient = paymentIntentClientSecret

                                    Log.d(TAG, "ParkingSpotScreen: $currentClient")
                                    if (currentClient != null) {
                                        presentPaymentSheet(currentClient, paymentSheet)
                                    }
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
        1 -> {
            PaymentCancelledInfoScreen()
        }
        2 -> {
            PaymentFailScreen()
        }
        3 -> {
            PaymentSuccessScreen()
        }
    }
}

private fun presentPaymentSheet(
    clientSecret: String,
    paymentSheet: PaymentSheet
) {
    paymentSheet.presentWithPaymentIntent(
        clientSecret,
        PaymentSheet.Configuration.Builder(merchantDisplayName = "Ayan Parking Solutions")
            .build()
    )

}