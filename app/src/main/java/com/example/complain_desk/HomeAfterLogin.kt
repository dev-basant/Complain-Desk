package com.example.complain_desk

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

data class ScreenItem(val title: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAfterLogin(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItem = remember { mutableStateOf("Home") }

    val firebaseAuth = FirebaseAuth.getInstance()

    val drawerItems = listOf(
        ScreenItem("Home", Icons.Default.Home),
        ScreenItem("Settings", Icons.Default.Settings),
        ScreenItem("Profile", Icons.Default.Person)
        // Removed Logout from here
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        "Main Menu",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    drawerItems.forEach { item ->
                        NavigationDrawerItem(
                            label = { Text(text = item.title) },
                            selected = (item.title == selectedItem.value),
                            onClick = {
                                selectedItem.value = item.title
                                scope.launch { drawerState.close() }
                            },
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f)) // Push Logout to bottom

                    // Logout item at the bottom
                    NavigationDrawerItem(
                        label = { Text("Logout") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            firebaseAuth.signOut()
                            navController.navigate("login") {
                                popUpTo("mainDash") { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Logout") },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = selectedItem.value) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                when (selectedItem.value) {
                    "Home" -> HomeScreen(modifier = Modifier.padding(innerPadding))
                    "Profile" -> ProfileScreen(modifier = Modifier.padding(innerPadding))
                    "Settings" -> SettingsScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text("Home Screen")
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text("Profile Screen")
    }
}

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text("Settings Screen")
    }
}
