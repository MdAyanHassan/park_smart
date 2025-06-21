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
import androidx.compose.material.icons.filled.Close // Alternative icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PaymentFailScreen(
    modifier: Modifier = Modifier,
    failureReason: String? = null, // Optional: more specific reason from backend/processor
    transactionId: String? = null, // Optional: if a transaction was attempted
    // Lambdas for actions if you decide to add buttons later, otherwise remove
    onRetryPayment: (() -> Unit)? = null,
    onContactSupport: (() -> Unit)? = null,
    onGoToHome: (() -> Unit)? = null // If you want a bail-out option
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
                imageVector = Icons.Default.Close, // Or Icons.Outlined.ReportProblem
                contentDescription = "Payment Failed Icon", // Hardcoded
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Payment Failed", // Hardcoded
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = failureReason ?: "Unfortunately, your payment could not be processed.", // Hardcoded default
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Please check your payment details and try again. You have not been charged for this attempt.", // Hardcoded
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            transactionId?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Transaction ID: $it", // Hardcoded with interpolation
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- Optional Action Buttons (Remove if not needed) ---
            onRetryPayment?.let {
                Button(
                    onClick = it,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Try Again") // Hardcoded
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            onGoToHome?.let {
                Button(
                    onClick = it,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text("Go to Homepage") // Hardcoded
                }
                Spacer(modifier = Modifier.height(12.dp))
            }


            onContactSupport?.let {
                TextButton(
                    onClick = it,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Contact Support") // Hardcoded
                }
            }
            // --- End Optional Action Buttons ---

            // If no action buttons, provide a hint for back navigation
            if (onRetryPayment == null && onContactSupport == null && onGoToHome == null) {
                Spacer(modifier = Modifier.height(8.dp)) // Adjust spacing as needed
                Text(
                    text = "Press back to return or try again.", // Hardcoded
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PaymentFailScreenPreviewWithActions() {
    MaterialTheme {
        PaymentFailScreen(
            failureReason = "Insufficient funds.",
            transactionId = "FAIL_TRANS_123",
            onRetryPayment = {},
            onContactSupport = {},
            onGoToHome = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PaymentFailScreenPreviewInfoOnly() {
    MaterialTheme {
        PaymentFailScreen(
            failureReason = "Network connection timed out during payment."
        )
    }
}