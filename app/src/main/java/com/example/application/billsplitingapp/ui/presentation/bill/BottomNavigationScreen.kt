package com.example.application.billsplitingapp.ui.presentation.bill

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class BottomNavigationScreen(val route: String, val name: String, val icon: ImageVector) {
    object Products: BottomNavigationScreen("products_screen", "Produtos", Icons.Default.ShoppingCart)
    object People: BottomNavigationScreen("people_screen", "Pessoas", Icons.Default.People)
}

sealed class PeopleScreens(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object AddEditPersonScreen : PeopleScreens(
        route = BottomNavigationScreen.People.route + "add-edit",
        arguments = listOf(
            navArgument(name = "billId") {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument(name = "personId") {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    )
}

