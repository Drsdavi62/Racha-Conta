package com.example.application.billsplitingapp.ui

import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class Screen(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {
    object Home : Screen(route = "home_screen")
    object History : Screen(route = "history_screen")
    object Bill : Screen(
        route = "bill_screen",
        arguments = listOf(navArgument(name = "billId") {
            type = NavType.IntType
            defaultValue = -1
        })
    )
}