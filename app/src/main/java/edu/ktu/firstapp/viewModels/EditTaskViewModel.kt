package edu.ktu.firstapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditTaskViewModel: ViewModel() {

    private lateinit var database: DatabaseReference

    private val _currentUser = MutableLiveData<FirebaseUser>(null)
    val currentUser: LiveData<FirebaseUser> = _currentUser

    init {
        _currentUser.value = FirebaseAuth.getInstance().currentUser
    }

    fun editTask(description: String, date: String) {

        if (description.isNullOrEmpty()) {
            return
        }
        if (date.isNullOrEmpty()) {
            return
        }

        val userId = _currentUser.value?.uid
        val database = FirebaseDatabase.getInstance()
        val taskRef = database.getReference("Tasks").child(userId.toString())

        val updatedData = mapOf(
            "description" to description,
            "date" to date
        )
        taskRef.updateChildren(updatedData)

    }



}