package com.example.complain_desk

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
        ScreenItem("Profile", Icons.Default.Person),
        ScreenItem("Settings", Icons.Default.Settings)
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
                    "Home" -> HomeScreen(modifier = Modifier.padding(innerPadding),navController)
                    "Profile" -> ProfileScreen(modifier = Modifier.padding(innerPadding))
                    "Settings" -> SettingsScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Welcome to Complain Desk 👋",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DashboardCard("New Complaint", Icons.Default.Add) {
                navController.navigate("addComplaint")
            }

            DashboardCard("My Complaints", Icons.Default.List) {
            navController.navigate("retriveComplaint")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DashboardCard("Resolved", Icons.Default.CheckCircle) {
                // TODO: Navigate to resolved list
            }

            DashboardCard("Rejected", Icons.Default.Close) {
                // TODO: Navigate to rejected list
            }
        }
    }
}


@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar Icon
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "User Icon",
            modifier = Modifier
                .size(100.dp)
                .padding(top = 24.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User Name
        Text(
            text = "Basant Kumar",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        // Email
        Text(
            text = "basantsharma762@gmail.com",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Settings Option Placeholder
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text("Account Settings", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Change Password")
                Text("Notification Settings")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Edit Profile Button
        Button(onClick = { /* TODO: Navigate to edit screen */ }) {
            Text("Edit Profile")
        }
    }
}


@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Notifications toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Enable Notifications")
            Switch(
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
        }

        // Dark mode toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Mode")
            Switch(
                checked = darkModeEnabled,
                onCheckedChange = { darkModeEnabled = it }
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Pushes the below content to the bottom

        // About section at bottom
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("About", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Complain Desk v1.0\nDeveloped by Basant Kumar")
            }
        }
    }
}




@Composable
fun DashboardCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier

            .height(120.dp)
            .padding(8.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = title, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title)
        }
    }
}
