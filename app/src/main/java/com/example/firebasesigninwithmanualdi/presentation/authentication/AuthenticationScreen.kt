package com.example.firebasesigninwithmanualdi.presentation.authentication

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasesigninwithmanualdi.core.Utils
import com.example.firebasesigninwithmanualdi.presentation.authentication.components.AuthAppBar
import com.example.firebasesigninwithmanualdi.presentation.authentication.components.AuthContent
import com.example.firebasesigninwithmanualdi.presentation.authentication.components.OneTapSignIn
import com.example.firebasesigninwithmanualdi.presentation.authentication.components.SignInWithGoogle
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun AuthenticationScreen(
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
    navigateToProfileScreen: () -> Unit
) {

    Scaffold(
        topBar = {
            AuthAppBar()
        }
    ) { innerPadding ->

        AuthContent(
            paddingValues = innerPadding,
            oneTapSignInClick = {
                authenticationViewModel.oneTapSignIn()
            }
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            try {
                val credentials = authenticationViewModel.oneTapClient.getSignInCredentialFromIntent(
                    activityResult.data
                )

                val googleIdToken = credentials.googleIdToken

                val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

                authenticationViewModel.signInWithGoogle(googleCredentials)
            } catch (e: ApiException) {
                Utils.printError(e)
            }
        }
    }

    fun launch(beginSignInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(beginSignInResult.pendingIntent.intentSender)
            .build()
        launcher.launch(intent)
    }

    OneTapSignIn(
        launch = {
            launch(it)
        }
    )

    SignInWithGoogle(
        navigateToHomeScreen = {
            if (it) {
                navigateToProfileScreen()
            }
        }
    )
}