package com.example.application.billsplitingapp.ui.presentation.bill

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationScreen(val route: String, val name: String, val icon: ImageVector) {
    object Products: BottomNavigationScreen("products_screen", "Produtos", Icons.Default.ShoppingCart)
    object People: BottomNavigationScreen("people_screen", "Pessoas", Icons.Default.People)
}
