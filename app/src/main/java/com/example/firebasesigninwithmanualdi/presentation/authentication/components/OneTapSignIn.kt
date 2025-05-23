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
import com.google.android.gms.auth.api.identity.BeginSignInResult

@Composable
fun OneTapSignIn(
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
    launch: (BeginSignInResult) -> Unit
) {
    val oneTapSignInResponse by authenticationViewModel.oneTapSignInResponse.collectAsState()

    when (oneTapSignInResponse) {
        is Response.Loading -> {
            ProgressBar()
        }
        is Response.Success -> {
            (oneTapSignInResponse as Response.Success<BeginSignInResult>).data?.let { beginSignInResult ->
                launch(beginSignInResult)
            }
        }
        is Response.Failure -> {
            LaunchedEffect(key1 = Unit) {
                Utils.printError((oneTapSignInResponse as Response.Failure).e)
            }
        }
    }

}