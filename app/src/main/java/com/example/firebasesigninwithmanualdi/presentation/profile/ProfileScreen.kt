package com.example.firebasesigninwithmanualdi.presentation.profile

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasesigninwithmanualdi.core.REVOKE_ACCESS_MESSAGE
import com.example.firebasesigninwithmanualdi.core.SIGN_OUT
import com.example.firebasesigninwithmanualdi.presentation.profile.components.ProfileContent
import com.example.firebasesigninwithmanualdi.presentation.profile.components.ProfileTopBar
import com.example.firebasesigninwithmanualdi.presentation.profile.components.RevokeAccess
import com.example.firebasesigninwithmanualdi.presentation.profile.components.SignOut
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.Unit

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory),
    navigateToAuthenticationScreen: () -> Unit,
    navigateToUsersScreen: () -> Unit,
    navigateToParkingSpotsScreen: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var parkingSpotsButtonClicked by rememberSaveable {
        mutableStateOf(false)
    }

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
            navigateToUsersScreen = navigateToUsersScreen,
            navigateToParkingSpotsScreen = {
                parkingSpotsButtonClicked = true
            }
        )
        if (parkingSpotsButtonClicked) {
            RequestPermissions(
                onParkingSpotsButtonClicked = {
                    parkingSpotsButtonClicked = false
                },
                navigateToParkingSpotsScreen = navigateToParkingSpotsScreen,
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope
            )
        }
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

@Composable
fun RequestPermissions(
    onParkingSpotsButtonClicked: () -> Unit,
    navigateToParkingSpotsScreen: () -> Unit,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    val context = LocalContext.current

    val locationPermissionRequest = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                navigateToParkingSpotsScreen()
            }

            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                navigateToParkingSpotsScreen()
            }

            else -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Location access denied"
                    )
                }
                return@rememberLauncherForActivityResult
            }
        }
        onParkingSpotsButtonClicked()
    }

    SideEffect {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                navigateToParkingSpotsScreen()
                onParkingSpotsButtonClicked()
            }

            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> {
                navigateToParkingSpotsScreen()
                onParkingSpotsButtonClicked()
            }

            else -> {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

}