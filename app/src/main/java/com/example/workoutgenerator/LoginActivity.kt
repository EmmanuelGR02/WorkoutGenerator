package com.example.workoutgenerator

import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// This class will contain the background code for the login screen
class LoginActivity(val username : String? = null, val password : String? = null) {


    // Checks if the given username and password are in the database
    // returns true if yes, false otherwise
    fun isLoginValid(msg : TextView, callback: (Boolean) -> Unit) {
        val arr = ArrayList<String>()

        database.child("user info").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    val username = i.child("username").value
                    val pswd = i.child("password").value
                    arr.add(pswd.toString())
                }
                val isLoginValid = arr.contains(password)
                callback(isLoginValid)
            }

            override fun onCancelled(p0: DatabaseError) {
                msg.text = "Invalid password"
                callback(false)
            }
        })
    }

}