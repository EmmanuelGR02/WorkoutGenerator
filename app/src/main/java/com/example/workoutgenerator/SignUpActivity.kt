
package com.example.workoutgenerator

import android.database.sqlite.SQLiteDatabase
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder


data class SignUpActivity(val name : String? = null, val lastName : String? = null, val username : String? = null, val password : String? = null)  {

    fun isValidInputs(msg: TextView, reEnteredPswd: String, callback: (Boolean) -> Unit) : Boolean {
        // check for errors
        val regex: Regex = ("[1234567890]").toRegex()


        if (name.isNullOrBlank() || lastName.isNullOrBlank() || username.isNullOrBlank() || password.isNullOrBlank()) {
            msg.text = "**Inputs cannot be left blank**"
            return false
        } else if (!password?.contains(regex)!!) {
            msg.text = "**password must contain at least 1 number**"
            return false
        } else if (reEnteredPswd != password) {
            msg.text = "**Passwords do not match**"
            return false
        } else if (password.length < 8) {
            msg.text = "**Password must contain at least 8 characters**"
            return false
        } else if (username?.length?.let { it < 5 } == true) {
            msg.text = "**username must contain at least 5 characters**"
            return false
        } else {
            // If all checks pass, perform username validation
            isUsernameValid(username) { isUsernameValid ->
                if (isUsernameValid) {
                    msg.text = "**username already exists**"
                }
                // Callback with the final validation result
                callback(!isUsernameValid)
            }
        }
        // If none of the above conditions are met, it means all inputs are valid.
        // Therefore, return true.
        return true
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
}

