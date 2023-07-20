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
        isUsernameValid(username.toString()) { isValid ->
            if (isValid) {

            } else {
                msg.text = "Invalid username"
            }
        }
    }


    private fun isUsernameValid(username: String, callback: (Boolean) -> Unit) {
        database.child("user info").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val arr = ArrayList<String>()
                for (i in p0.children) {
                    val user = i.child("username").value
                    arr.add(user.toString())
                }
                val isUsernameValid = arr.contains(username)
                callback(isUsernameValid)
            }

            override fun onCancelled(p0: DatabaseError) {
                callback(false)
            }
        })
    }

    private fun isPasswordValid(password : String, callback : (Boolean) -> Unit) {
        database.child("user info").child(username.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val arr = ArrayList<String>()

                for (i in snapshot.children) {
                    val pswd = i.child("password").value
                    arr.add(pswd.toString())
                }
                val isPasswordValid = arr.contains(password)
                callback(isPasswordValid)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }

        })
    }

}