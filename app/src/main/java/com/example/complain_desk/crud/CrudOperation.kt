package com.example.complain_desk.crud

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreRepository {

    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("complaints")

    fun addComplaint(userId: String, issue: String, desp: String, onResult: (Boolean) -> Unit) {
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
}
