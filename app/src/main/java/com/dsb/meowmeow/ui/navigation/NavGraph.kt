package com.dsb.meowmeow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dsb.meowmeow.ui.screens.home.HomeScreen

@Composable
fun MeowNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(title = Screen.Home.title)
        }
    }
}