package com.example.complain_desk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.navigation.compose.rememberNavController
//import com.example.complain_desk.ui.theme.Complain_DeskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            Complain_DeskTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
//
//                 HomeScreen()
//
//                }
//            }
            val navController = rememberNavController() // Navigation Controller
            AppNavHost(navController) // Call the navigation function
        }
    }
}