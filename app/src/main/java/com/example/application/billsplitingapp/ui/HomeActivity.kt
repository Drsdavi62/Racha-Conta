package com.example.application.billsplitingapp.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.ui.Screen
import com.example.application.billsplitingapp.ui.presentation.history.HistoryScreen
import com.example.application.billsplitingapp.ui.presentation.homeFragment.HomeScreen
import com.example.application.billsplitingapp.ui.theme.BillSplitingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {
            BillSplitingAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route
                    ) {
                        composable(
                            route = Screen.Home.route
                        ) {
                            HomeScreen(navController)
                        }
                        composable(
                            route = Screen.History.route
                        ) {
                            HistoryScreen(navController)
                        }
                    }
                }
            }
        }
    }
}