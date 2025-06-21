package com.example.firebasesigninwithmanualdi.presentation.parking_spots.components // Assuming a similar package

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle // Standard success icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // For custom success color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Define a success color (consider moving to your Theme if used elsewhere)
val SuccessGreen = Color(0xFF4CAF50) // Material Green 500

@Composable
fun PaymentSuccessScreen(
    modifier: Modifier = Modifier,
    orderNumber: String? = null,
    confirmationMessage: String? = null,
    // Optional actions
    onViewOrderDetails: (() -> Unit)? = null,
    onContinueShopping: (() -> Unit)? = null,
    onGoToDashboard: (() -> Unit)? = null
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(all = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle, // Or Icons.Outlined.TaskAlt
                contentDescription = "Payment Successful Icon", // Hardcoded
                modifier = Modifier.size(120.dp),
                tint = SuccessGreen // Use a distinct success color
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Payment Successful!", // Hardcoded
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = confirmationMessage ?: "Your payment has been processed successfully. Thank you!", // Hardcoded default
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            orderNumber?.let {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Order Number:", // Hardcoded
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = it, // Display the order number
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary // Highlight order number
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- Optional Action Buttons ---
            onViewOrderDetails?.let {
                Button(
                    onClick = it,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("View Order Details") // Hardcoded
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            onContinueShopping?.let {
                Button(
                    onClick = it,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text("Continue Shopping") // Hardcoded
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            onGoToDashboard?.let {
                TextButton(
                    onClick = it,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Go to My Dashboard") // Hardcoded
                }
            }
            // --- End Optional Action Buttons ---

            // If no action buttons, provide a hint for back navigation (less common for success)
            if (onViewOrderDetails == null && onContinueShopping == null && onGoToDashboard == null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "You can now close this page or press back.", // Hardcoded
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
fun PaymentSuccessScreenPreviewWithActions() {
    MaterialTheme {
        PaymentSuccessScreen(
            orderNumber = "ORD-2024-MAR-00123",
            confirmationMessage = "Your items will be shipped soon. A confirmation email has been sent.",
            onViewOrderDetails = {},
            onContinueShopping = {},
            onGoToDashboard = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 780)
@Composable
fun PaymentSuccessScreenPreviewInfoOnly() {
    MaterialTheme {
        PaymentSuccessScreen(
            orderNumber = "ORD-2024-MAR-00456"
        )
    }
}