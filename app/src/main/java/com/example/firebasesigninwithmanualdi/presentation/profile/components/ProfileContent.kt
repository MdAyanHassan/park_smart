package com.example.firebasesigninwithmanualdi.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlin.Unit

@Composable
fun ProfileContent(
    paddingValues: PaddingValues,
    displayName: String,
    photoUrl: String,
    navigateToUsersScreen: () -> Unit,
    navigateToParkingSpotsScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))
        
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .height(96.dp)
                .width(96.dp)
        )
        Text(
            text = displayName,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(onClick = { navigateToUsersScreen() }) {
            Text(text = "Get all users")
        }

        Button(onClick = { navigateToParkingSpotsScreen() }) {
            Text(text = "Get parking spots")
        }

    }
}