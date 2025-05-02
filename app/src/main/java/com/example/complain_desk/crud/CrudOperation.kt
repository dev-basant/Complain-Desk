package com.example.complain_desk.crud

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreRepository {

    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("complaints")
    val userId = FirebaseAuth.getInstance().currentUser?.uid



    fun addComplaint(issue: String, desp: String, onResult: (Boolean) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            onResult(false)
            return
        }
        val complaint = hashMapOf(
            "issue" to issue,
            "desp" to desp,
            "timeStamp" to System.currentTimeMillis()
        )
        val userDoc = userCollection.document(userId)

        userDoc.get().addOnSuccessListener { document ->
            if (document.exists()) {
                // Append to existing array
                userDoc.update("complaints", FieldValue.arrayUnion(complaint))
                    .addOnSuccessListener { onResult(true) }
                    .addOnFailureListener { onResult(false) }
            } else {
                // Create document with array
                val complaintData = hashMapOf(
                    "complaints" to listOf(complaint)
                )
                userDoc.set(complaintData)
                    .addOnSuccessListener { onResult(true) }
                    .addOnFailureListener { onResult(false) }
            }
        }.addOnFailureListener {
            onResult(false)
        }
    }

    fun getComplaint(userId: String, onResult: (List<String>) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            onResult(listOf("User not logged in"))
            return
        }
        userCollection.document(userId).get()
            .addOnSuccessListener { document ->
                val complaints = document["complaints"] as? List<Map<String, Any>>
                val formatted = complaints?.map {
                    val issue = it["issue"] as? String
                    val desp = it["desp"] as? String
                    if (issue != null && desp != null) "Issue: $issue\nDescription: $desp" else null
                }?.filterNotNull() ?: listOf("No Complaints Found")
                onResult(formatted)
            }
            .addOnFailureListener {
                onResult(listOf("Failed to fetch"))
            }
    }
    fun getComplaint1(userId: String, onResult: (List<String>) -> Unit) {

        if (userId == null) {
            onResult(listOf("User not logged in"))
            return
        }
        userCollection.document(userId).get()
            .addOnSuccessListener { document ->
                val complaints = document["complaints"] as? List<Map<String, Any>>
                val formatted = complaints?.map {
                    val issue = it["issue"] as? String
                    val desp = it["desp"] as? String
                    if (issue != null && desp != null) "Issue: $issue\nDescription: $desp" else null
                }?.filterNotNull() ?: listOf("No Complaints Found")
                onResult(formatted)
            }
            .addOnFailureListener {
                onResult(listOf("Failed to fetch"))
            }
    }

    // code for user detail

    private val userDetailCollection = db.collection("userDetail")

    fun userDetail(userName: String, age: String, location: String, occupation: String ) {

        val user = hashMapOf(
            "userName" to userName,
            "age" to age,
            "location" to location,
            "occupation" to occupation
        )

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            userDetailCollection.document(userId).set(user)

        }
    }
    fun getUserDetails(
        onSuccess: (Map<String, Any>) -> Unit,
        onError: (String) -> Unit
    ) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            userDetailCollection.document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        onSuccess(document.data ?: emptyMap())
                    } else {
                        onError("No data found")
                    }
                }
                .addOnFailureListener { e ->
                    onError(e.localizedMessage ?: "Error fetching data")
                }
        } else {
            onError("User not logged in")
        }
    }

}
