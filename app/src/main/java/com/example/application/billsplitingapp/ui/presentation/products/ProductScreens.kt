package com.example.application.billsplitingapp.ui.presentation.products

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument
import com.example.application.billsplitingapp.ui.presentation.bill.BottomNavigationScreen

sealed class ProductScreens(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object AddEditProductScreen : ProductScreens(
        route = BottomNavigationScreen.Products.route + "add-edit",
        arguments = listOf(
            navArgument(name = "billId") {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument(name = "productId") {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    )
}
