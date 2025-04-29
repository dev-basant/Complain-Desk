package com.example.complain_desk.crud

import androidx.lifecycle.ViewModel

class CompaintViewmodel:ViewModel() {

    private val repo = FireStoreRepository()

    fun addComplaint(userId:String,issue:String,desp:String,onResult: (Boolean)->Unit){
       repo.addComplaint(userId,issue,desp,onResult)
    }
   fun getComplaint(userId: String,onResult: (List<String>) -> Unit){
       repo.getComplaint(userId,onResult)
   }


}