package com.example.firebasesigninwithmanualdi.navigation

import com.example.firebasesigninwithmanualdi.core.AUTH_ROUTE
import com.example.firebasesigninwithmanualdi.core.NAVIGATION_ROUTE
import com.example.firebasesigninwithmanualdi.core.PARKING_SPOTS_ROUTE
import com.example.firebasesigninwithmanualdi.core.PROFILE_ROUTE
import com.example.firebasesigninwithmanualdi.core.USERS_ROUTE

sealed class Screen(val route: String) {
    data object Authentication: Screen(AUTH_ROUTE)
    data object Profile: Screen(PROFILE_ROUTE)
    data object Users: Screen(USERS_ROUTE)
    data object ParkingSpots: Screen(PARKING_SPOTS_ROUTE)
    data object Navigation: Screen(NAVIGATION_ROUTE)
}