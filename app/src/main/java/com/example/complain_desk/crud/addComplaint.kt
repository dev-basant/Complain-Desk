package com.example.complain_desk.crud

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun AddComplaintScreen(
    viewmodel: CompaintViewmodel = viewModel(),
    navController: NavController
) {
    var userId by remember { mutableStateOf("") }
    var issue by remember { mutableStateOf("") }
    var desp by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Submit a Complaint",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = userId,
                onValueChange = { userId = it },
                label = { Text("User ID") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = issue,
                onValueChange = { issue = it },
                label = { Text("Issue Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = desp,
                onValueChange = { desp = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5
            )

            Button(
                onClick = {
                    if (userId.isBlank() || issue.isBlank() || desp.isBlank()) {
                        result = "Please fill all fields"
                        return@Button
                    }

                    isSubmitting = true
                    viewmodel.addComplaint(userId, issue, desp) {
                        result = if (it) "Complaint submitted!" else "Failed to submit."
                        isSubmitting = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isSubmitting
            ) {
                Text(if (isSubmitting) "Submitting..." else "Submit Complaint")
            }

            if (result.isNotBlank()) {
                Text(
                    text = result,
                    color = if ("Failed" in result) Color.Red else Color(0xFF2E7D32), // Green
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "Search Complaints ➜",
                modifier = Modifier
                    .clickable { navController.navigate("retriveComplaint") }
                    .padding(top = 12.dp),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
