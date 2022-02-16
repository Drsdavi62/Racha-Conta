package com.example.application.billsplitingapp.ui.presentation.bill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.application.billsplitingapp.ui.components.BackTitleHeader
import com.example.application.billsplitingapp.ui.presentation.bill.people.PeopleScreen
import com.example.application.billsplitingapp.ui.presentation.products.list.ProductListScreen
import com.example.application.billsplitingapp.ui.presentation.products.ProductScreens
import com.example.application.billsplitingapp.ui.presentation.products.add_edit.AddEditProductScreen

@Composable
fun BillScreen(mainNavController: NavController, viewModel: BillViewModel = hiltViewModel()) {

    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreen.Products,
        BottomNavigationScreen.People
    )

    val bill = viewModel.bill.value

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(65.dp)
                    .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                cutoutShape = CircleShape
            ) {
                BottomNavigationBar(navController, bottomNavigationItems)
            }
        },
        floatingActionButton = {
            if (navBackStackEntry?.destination?.route == BottomNavigationScreen.Products.route
                || navBackStackEntry?.destination?.route == BottomNavigationScreen.People.route) {
                FloatingActionButton(onClick = {
                    navController.navigate(
                        if (navBackStackEntry?.destination?.route == BottomNavigationScreen.Products.route) {
                            ProductScreens.AddEditProductScreen.route + "/?billId=${bill?.id}"
                        } else {
                            ProductScreens.AddEditProductScreen.route + "/?billId=${bill?.id}"
                        }
                    )
                }) {
                    Icon(
                        imageVector = when (navBackStackEntry?.destination?.route) {
                            BottomNavigationScreen.Products.route -> {
                                Icons.Default.AddShoppingCart
                            }
                            BottomNavigationScreen.People.route -> {
                                Icons.Default.GroupAdd
                            }
                            else -> {
                                Icons.Default.Add
                            }
                        },
                        contentDescription = "Add product"
                    )
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            BackTitleHeader(
                title = bill?.name ?: "Comanda",
                navController = mainNavController,
                modifier = Modifier.padding(top = 16.dp)
            )
            NavHost(navController, startDestination = BottomNavigationScreen.Products.route) {
                composable(
                    route = BottomNavigationScreen.Products.route,
                ) {
                    ProductListScreen(navController = navController, billId = bill?.id ?: -1)
                }
                composable(
                    route = ProductScreens.AddEditProductScreen.route + "/?billId={billId}&productId={productId}",
                    arguments = ProductScreens.AddEditProductScreen.arguments
                ) {
                    AddEditProductScreen(navController = navController)
                }
                composable(BottomNavigationScreen.People.route) {
                    PeopleScreen(billId = bill?.id ?: -1)
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
    BottomNavigation(
        elevation = 0.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        items.forEach { screen ->
            println(navBackStackEntry?.destination?.route)
            println(screen.route)
            BottomNavigationItem(
                icon = { Icon(screen.icon, screen.name) },
                label = { Text(screen.name) },
                selected = navBackStackEntry?.destination?.route?.contains(screen.route) ?: false,
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