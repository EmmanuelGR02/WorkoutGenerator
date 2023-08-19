package com.example.workoutgenerator

import android.util.Log
import android.widget.TextView
import androidx.annotation.ContentView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// This class will contain the background code for the login screen
data class LoginActivity(val username : String? = null, val password : String? = null) {

    fun getName(callback: (name: String?) -> Unit) {
        Database.getInstance().isUsernameValid(username.toString()) { isValid ->
            if (isValid) {
                Database.getInstance().getUserInfo(username.toString(), "user info") { snapshot ->
                    val name = snapshot?.child("name")?.value?.toString() ?: "N/A"
                    callback(name)
                }
            } else {
                callback(null) // Username does not exist, callback with null name
            }
        }
    }

    // Checks if the given username and password are in the database
    fun isLoginValid(msg : TextView, callback: (Boolean) -> Unit) {
        Database.getInstance().isUsernameValid(username.toString()) { isUsernameValid ->
            if (!isUsernameValid) {
                Database.getInstance().isPasswordValid(password.toString(), username.toString()) { isPswdValid ->
                    if(isPswdValid) {
                        callback(true)
                    } else {
                        msg.text = "Invalid credentials"
                        callback(false)
                    }
                }
            } else {
                msg.text = "Invalid credentials"
                callback(false)
            }
        }
    }
}