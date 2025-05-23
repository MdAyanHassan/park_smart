package com.example.firebasesigninwithmanualdi.presentation.authentication.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasesigninwithmanualdi.components.ProgressBar
import com.example.firebasesigninwithmanualdi.core.Utils
import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.presentation.authentication.AuthenticationViewModel

@Composable
fun SignInWithGoogle(
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
    navigateToHomeScreen: (Boolean) -> Unit
) {
    val signInWithGoogleResponse by authenticationViewModel.googleSignInResponse.collectAsState()

    when (signInWithGoogleResponse) {
        is Response.Loading -> {
            ProgressBar()
        }
        is Response.Success -> {
            (signInWithGoogleResponse as Response.Success<Boolean>).data?.let { signedIn ->
                LaunchedEffect(key1 = signedIn) {
                    navigateToHomeScreen(signedIn)
                }
            }
        }
        is Response.Failure -> {
            LaunchedEffect(key1 = Unit) {
                Utils.printError((signInWithGoogleResponse as Response.Failure).e)
            }
        }
    }
}

