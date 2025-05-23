package com.example.firebasesigninwithmanualdi.presentation.authentication.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.firebasesigninwithmanualdi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthAppBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.auth_title))
        }
    )
}