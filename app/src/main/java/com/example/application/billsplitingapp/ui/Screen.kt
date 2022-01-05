package com.example.application.billsplitingapp.ui

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object History: Screen(route = "history_screen")
}