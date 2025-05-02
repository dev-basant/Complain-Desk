package com.example.complain_desk.crud

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CompaintViewmodel:ViewModel() {


    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val userDetailCollection = db.collection("userDetail")
    private val repo = FireStoreRepository()

    fun addComplaint(issue:String,desp:String,onResult: (Boolean)->Unit){
       repo.addComplaint(issue,desp,onResult)
    }
   fun getComplaint(userId: String,onResult: (List<String>) -> Unit){
       repo.getComplaint(userId,onResult)
   }
    fun getComplaint1(userId: String,onResult: (List<String>) -> Unit){
       repo.getComplaint1(userId,onResult)
   }

    fun userDetail(userName: String, age: String, location: String, occupation: String) {
        repo.userDetail(userName,age,location,occupation)

    }
    fun getUserDetails(
        onSuccess: (Map<String, Any>) -> Unit,
        onError: (String) -> Unit
    ) {
        userId?.let {
            userDetailCollection.document(it).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        onSuccess(document.data ?: emptyMap())
                    } else {
                        onError("No user details found")
                    }
                }
                .addOnFailureListener { e ->
                    onError(e.localizedMessage ?: "Error fetching user details")
                }
        } ?: onError("User not logged in")
    }

}