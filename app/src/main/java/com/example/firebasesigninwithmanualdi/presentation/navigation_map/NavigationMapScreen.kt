package com.example.firebasesigninwithmanualdi.presentation.navigation_map

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.firebasesigninwithmanualdi.NavigationMapActivity

@Composable
fun NavigationMapScreen(
    destinationPlaceId: String,
    onMapLaunched: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val mapIntent = Intent(context, NavigationMapActivity::class.java).apply {
            putExtra("destPlaceId", destinationPlaceId)
        }

        context.startActivity(mapIntent)

        onMapLaunched()
    }
}