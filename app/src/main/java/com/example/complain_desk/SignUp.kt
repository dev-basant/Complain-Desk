package com.example.complain_desk

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun SignupScreen(navController: NavController, firebaseAuth: FirebaseAuth) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        if (task.isSuccessful) {
            val account = task.result
            val email = account?.email
            val idToken = account?.idToken

            if (email != null && idToken != null) {
                // 🔐 Step 1: Check if user already exists
                firebaseAuth.fetchSignInMethodsForEmail(email)
                    .addOnSuccessListener { result ->
                        val signInMethods = result.signInMethods ?: emptyList()

                        if (signInMethods.isNotEmpty()) {
                            // ✅ Already registered → don't proceed
                            Toast.makeText(
                                context,
                                "User already registered with this Google account",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            // ✅ Not registered → sign up
                            val credential = GoogleAuthProvider.getCredential(idToken, null)
                            firebaseAuth.signInWithCredential(credential)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        context,
                                        "Signed up with Google!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate("mainDash")
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        context,
                                        "Signup failed: ${it.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(context, "Failed to retrieve Google credentials", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Google sign-in failed", Toast.LENGTH_SHORT).show()
        }
    }





    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Signup", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Done else Icons.Filled.Lock,
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            user?.sendEmailVerification()?.addOnCompleteListener { verifyTask ->
                                if (verifyTask.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Verification email sent to $email",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    firebaseAuth.signOut()
                                    navController.navigate("login")
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Failed to send verification email",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Signup Failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }) {
            Text("SignUp")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("658774340481-f5jn3h47d77ie8p01igsvtk6iu580hca.apps.googleusercontent.com") // your client ID
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(context, gso)

            // 💡 Force account chooser popup
            googleSignInClient.signOut().addOnCompleteListener {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.gmail_svgrepo_com), // Your Google icon in drawable folder
                contentDescription = "Google Icon",
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "SignUp with Google")
        }


        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text("Already have an account? Login")
        }

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}
