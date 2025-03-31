package com.example.complain_desk

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {  // Default screen is Home
        composable("home") { HomeScreen(navController) }  // Home screen
        composable("login") { LoginScreen(navController) } // Login screen
        composable("signup") { SignupScreen(navController) }
        composable("mainDash") { HomeAfterLogin(navController) }// Signup screen
    }
}
