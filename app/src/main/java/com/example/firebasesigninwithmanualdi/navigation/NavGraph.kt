package com.example.firebasesigninwithmanualdi.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firebasesigninwithmanualdi.presentation.authentication.AuthenticationScreen
import com.example.firebasesigninwithmanualdi.presentation.profile.ProfileScreen
import com.example.firebasesigninwithmanualdi.presentation.users.UsersScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Authentication.route,
    ) {

        composable(route = Screen.Authentication.route) {
            AuthenticationScreen(
                navigateToProfileScreen = {
                    navController.popBackStack()
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(
                navigateToAuthenticationScreen = {
                    navController.popBackStack()

                    navController.navigate(Screen.Authentication.route)
                },
                navigateToUsersScreen = {
                    navController.navigate(Screen.Users.route)
                }
            )
        }

        composable(route = Screen.Users.route) {
            UsersScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
    }
}