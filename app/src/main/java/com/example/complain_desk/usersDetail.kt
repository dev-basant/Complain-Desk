package com.example.complain_desk.crud

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun userDetail(
    viewmodel: CompaintViewmodel,
    navController: NavController,
    firebaseAuth: FirebaseAuth
){
    var userName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var occupation by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(value = userName, onValueChange = {userName = it}, label = { Text("Name") })
        OutlinedTextField(value = age, onValueChange = {age = it}, label = { Text("Age") })
        OutlinedTextField(value = location, onValueChange = {location = it}, label = { Text("location") })
        OutlinedTextField(value = occupation, onValueChange = {occupation = it}, label = { Text("occupation") })

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if(userName.isBlank() || age.isBlank()||location.isBlank() ||occupation.isBlank()){
                    Toast.makeText(context,"All Fields are Mandatory",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    viewmodel.userDetail(userName,age,location,occupation)

                    navController.navigate("mainDash")

                }
            }
        ) {
            Text("Submitt Detail")
        }

    }

}
