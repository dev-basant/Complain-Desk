package com.example.complain_desk

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.complain_desk.crud.CompaintViewmodel
import com.example.complain_desk.crud.AddComplaintScreen
import com.example.complain_desk.crud.FetchComplaintsAfter
import com.example.complain_desk.crud.FetchComplaintsBefore
import com.example.complain_desk.crud.userDetail
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavHost(navController: NavHostController,firebaseAuth:FirebaseAuth) {
    NavHost(navController, startDestination = "splash") {  // Default screen is Home
        composable("home") { HomeScreen(navController = navController) }  // Home screen
        composable("login") { LoginScreen(navController,firebaseAuth) } // Login screen
        composable("signup") { SignupScreen(navController,firebaseAuth) }
        composable("mainDash") { HomeAfterLogin(navController) }// Signup screen
        composable("beforeLogin") { HomeScreen(navController,firebaseAuth) }
        composable("addComplaint") { AddComplaintScreen(viewmodel = CompaintViewmodel() ,navController) }
        composable("retriveComplaintB") { FetchComplaintsBefore(viewmodel = CompaintViewmodel()) }
        composable("retriveComplaintA") { FetchComplaintsAfter(viewmodel = CompaintViewmodel()) }
        composable("splash"){splash(navController,firebaseAuth)}
        composable("userDetail") { userDetail(viewmodel = CompaintViewmodel(),navController,firebaseAuth) }
    }
}
