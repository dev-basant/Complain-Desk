package com.example.complain_desk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Preview(showSystemUi = true, showBackground = true)
@Composable

fun HomeScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 45.dp),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
                .height(70.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Welcome To Complain Desk",
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = { navController.navigate("login") }, // Navigate to Login
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account",
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = {}) {
                Text("Check Status")
            }
        }
    }

    // Show the login/signup dialog when showDialog is true
//    if (showDialog) {
//        AlertDialog(
//            onDismissRequest = { showDialog = false },
//            title = { Text("Login / Signup") },
//            text = { Text("Choose an option to continue.") },
//            confirmButton = {
//                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
//                    Button(onClick = { /* Navigate to Login */ showDialog = false }) {
//                        Text("Login")
//                    }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Button(onClick = { /* Navigate to Signup */ showDialog = false }) {
//                        Text("Signup")
//                    }
//                }
//            },
//            dismissButton = {
//                Button(onClick = { showDialog = false }) {
//                    Text("Cancel")
//                }
//            }
//        )
//    }
}
