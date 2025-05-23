package com.example.firebasesigninwithmanualdi.presentation.profile.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasesigninwithmanualdi.components.ProgressBar
import com.example.firebasesigninwithmanualdi.core.Utils
import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.presentation.profile.ProfileViewModel

@Composable
fun RevokeAccess(
    profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory),
    navigateToAuthenticationScreen: (Boolean) -> Unit,
    showSnackbar: () -> Unit
) {
    val revokeAccessResponse by profileViewModel.revokeAccessResponse.collectAsState()

    when (revokeAccessResponse) {
        is Response.Failure -> {
            LaunchedEffect(key1 = Unit) {
                Utils.printError((revokeAccessResponse as Response.Failure).e)
                showSnackbar()
            }
        }
        Response.Loading -> {
            ProgressBar()
        }
        is Response.Success -> {
            (revokeAccessResponse as Response.Success).data?.let {
                LaunchedEffect(key1 = it) {
                    navigateToAuthenticationScreen(it)
                }
            }
        }
    }
}