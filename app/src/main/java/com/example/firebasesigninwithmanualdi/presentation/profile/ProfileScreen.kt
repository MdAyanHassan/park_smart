package com.example.firebasesigninwithmanualdi.presentation.profile

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasesigninwithmanualdi.core.REVOKE_ACCESS_MESSAGE
import com.example.firebasesigninwithmanualdi.core.SIGN_OUT
import com.example.firebasesigninwithmanualdi.presentation.profile.components.ProfileContent
import com.example.firebasesigninwithmanualdi.presentation.profile.components.ProfileTopBar
import com.example.firebasesigninwithmanualdi.presentation.profile.components.RevokeAccess
import com.example.firebasesigninwithmanualdi.presentation.profile.components.SignOut
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory),
    navigateToAuthenticationScreen: () -> Unit,
    navigateToUsersScreen: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ProfileTopBar(
                signOut = {
                    profileViewModel.signOut()
                },
                revokeAccess = {
                    profileViewModel.revokeAccess()
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        ProfileContent(
            paddingValues = paddingValues,
            displayName = profileViewModel.displayName,
            photoUrl = profileViewModel.photoUrl,
            navigateToUsersScreen = navigateToUsersScreen
        )
    }

    SignOut(
        navigateToAuthenticationScreen = {
            if (it) {
                navigateToAuthenticationScreen()
            }
        }
    )

    fun showSnackbar() {

        coroutineScope.launch {
            val result = snackbarHostState.showSnackbar(
                message = REVOKE_ACCESS_MESSAGE,
                actionLabel = SIGN_OUT
            )

            if (result == SnackbarResult.ActionPerformed) {
                profileViewModel.signOut()
            }
        }

    }

    RevokeAccess(
        navigateToAuthenticationScreen = {
            if (it) {
                navigateToAuthenticationScreen()
            }
        },
        showSnackbar = {
            showSnackbar()
        }
    )
}