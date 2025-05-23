package com.example.firebasesigninwithmanualdi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.firebasesigninwithmanualdi.navigation.NavGraph
import com.example.firebasesigninwithmanualdi.navigation.Screen
import com.example.firebasesigninwithmanualdi.presentation.authentication.AuthenticationViewModel
import com.example.firebasesigninwithmanualdi.ui.theme.FirebaseSignInWithManualDITheme

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