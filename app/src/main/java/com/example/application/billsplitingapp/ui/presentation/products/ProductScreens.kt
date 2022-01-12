package com.example.application.billsplitingapp.ui.presentation.products

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

sealed class ProductScreens(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object ProductListScreen : ProductScreens(
        "product_list_screen", arguments = listOf(
            navArgument(name = "billId") {
                type = NavType.IntType
                defaultValue = -1
            })
    )

    object AddEditProductScreen : ProductScreens(
        route = "add_edit_product_screen",
        arguments = listOf(
            navArgument(name = "billId") {
                type = NavType.IntType
                defaultValue = -1
            })
    )
}
