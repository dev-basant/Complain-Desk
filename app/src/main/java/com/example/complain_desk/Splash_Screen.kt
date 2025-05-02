package com.example.complain_desk

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@Composable
fun splash(navController: NavController,firebaseAuth: FirebaseAuth) {

    LaunchedEffect(true) {
        val user = firebaseAuth.currentUser
        delay(100)
        if (user != null) {
            navController.navigate("mainDash") {
                popUpTo("splash") {
                    inclusive = true
                }
            }
        } else {
            navController.navigate("beforeLogin") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
}