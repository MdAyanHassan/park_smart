package com.example.firebasesigninwithmanualdi.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firebasesigninwithmanualdi.presentation.authentication.AuthenticationScreen
import com.example.firebasesigninwithmanualdi.presentation.navigation_map.NavigationMapScreen
import com.example.firebasesigninwithmanualdi.presentation.parking_spots.ParkingSpotScreen
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
                },
                navigateToParkingSpotsScreen = {
                    navController.navigate(Screen.ParkingSpots.route)
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

        composable(route = Screen.ParkingSpots.route) {
            ParkingSpotScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onNavigationClick = { destPlaceId, destPlaceTitle ->
                    navController.navigate(Screen.Navigation.route.replace("{destPlaceId}", destPlaceId ?: ""))
                }
            )
        }

        composable(route = Screen.Navigation.route) {
            NavigationMapScreen(
                destinationPlaceId = it.arguments?.getString("destPlaceId") ?: "",
                onMapLaunched = {
                    navController.popBackStack()
                }
            )
        }
        
    }
}