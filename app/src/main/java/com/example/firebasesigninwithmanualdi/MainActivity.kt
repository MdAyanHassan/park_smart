package com.example.firebasesigninwithmanualdi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.firebasesigninwithmanualdi.navigation.NavGraph
import com.example.firebasesigninwithmanualdi.navigation.Screen
import com.example.firebasesigninwithmanualdi.presentation.authentication.AuthenticationViewModel
import com.example.firebasesigninwithmanualdi.ui.theme.FirebaseSignInWithManualDITheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    private val authenticationViewModel: AuthenticationViewModel by viewModels(
        factoryProducer = {
            AuthenticationViewModel.Factory
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            navController = rememberNavController()
            FirebaseSignInWithManualDITheme {
                NavGraph(navController = navController)
                checkAuthenticationState()
            }
        }
    }

    private fun checkAuthenticationState() {
        if (authenticationViewModel.isUserAuthenticated) {
            navController.navigate(Screen.Profile.route)
        }
    }
}