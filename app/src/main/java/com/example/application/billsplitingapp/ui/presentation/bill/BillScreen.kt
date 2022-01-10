package com.example.application.billsplitingapp.ui.presentation.bill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.application.billsplitingapp.ui.components.BackTitleHeader
import com.example.application.billsplitingapp.ui.presentation.bill.People.PeopleScreen
import com.example.application.billsplitingapp.ui.presentation.bill.Products.ProductScreen

@Composable
fun BillScreen(mainNavController: NavController, viewModel: BillViewModel = hiltViewModel()) {

    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreen.Products,
        BottomNavigationScreen.People
    )

    val bill = viewModel.bill.value

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, bottomNavigationItems)
        }
    ) {
        Column {
            BackTitleHeader(
                title = bill?.name ?: "Comanda",
                navController = mainNavController,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            NavHost(navController, startDestination = BottomNavigationScreen.Products.route) {
                composable(BottomNavigationScreen.Products.route) {
                    ProductScreen()
                }
                composable(BottomNavigationScreen.People.route) {
                    PeopleScreen()
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<BottomNavigationScreen>
) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, screen.name) },
                label = { Text(screen.name) },
                selected = navBackStackEntry?.destination?.route == screen.route,
                alwaysShowLabel = false,// This hides the title for the unselected items
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (navBackStackEntry?.destination?.route != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}