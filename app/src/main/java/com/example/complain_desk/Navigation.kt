package com.example.complain_desk

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.complain_desk.crud.CompaintViewmodel
import com.example.complain_desk.crud.AddComplaintScreen
import com.example.complain_desk.crud.FetchComplaints
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavHost(navController: NavHostController,firebaseAuth:FirebaseAuth) {
    NavHost(navController, startDestination = "beforeLogin") {  // Default screen is Home
        composable("home") { HomeScreen(navController = navController) }  // Home screen
        composable("login") { LoginScreen(navController,firebaseAuth) } // Login screen
        composable("signup") { SignupScreen(navController,firebaseAuth) }
        composable("mainDash") { HomeAfterLogin(navController) }// Signup screen
        composable("beforeLogin") { HomeScreen(navController,firebaseAuth) }
        composable("addComplaint") { AddComplaintScreen(viewmodel = CompaintViewmodel() ,navController) }
        composable("retriveComplaint") { FetchComplaints(viewmodel = CompaintViewmodel()) }

    }
}
