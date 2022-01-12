package com.example.application.billsplitingapp.ui.presentation.products

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.application.billsplitingapp.ui.presentation.products.add_edit.AddEditProductScreen

@Composable
fun ProductMainScreen(billId: Int) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ProductScreens.ProductListScreen.route + "/?billId={billId}"
    ) {
        composable(
            route = ProductScreens.ProductListScreen.route + "/?billId={billId}",
            arguments = listOf(navArgument("billId") {type = NavType.IntType; defaultValue = billId})
        ) { backStackEntry ->
            val id = (backStackEntry.arguments?.get("billId") as? Int)
            ProductListScreen(navController = navController, billId = id ?: -1)
        }
        composable(
            route = ProductScreens.AddEditProductScreen.route + "/?billId={billId}",
            arguments = ProductScreens.AddEditProductScreen.arguments
        ) {
            AddEditProductScreen(navController = navController)
        }
    }

}