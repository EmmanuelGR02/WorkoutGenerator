
package com.example.workoutgenerator

import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


data class SignUpActivity(val name : String? = null, val lastName : String? = null, val username : String? = null, val password : String? = null)  {

    fun isValidInputs (msg: TextView, reEnteredPswd : String) : Boolean {

        // check for errors
        val regex: Regex = ("[1234567890]").toRegex()

        if (name.isNullOrBlank() || lastName.isNullOrBlank() || username.isNullOrBlank() || password.isNullOrBlank()) {
            msg.text = "**Inputs cannot be left blank**"
            return false
        }else if (!password?.contains(regex)!!) {
            msg.text = "**password must contain at least 1 number**"
            return false
        }else if (reEnteredPswd != password) {
            msg.text = "**Passwords do not match**"
            return false
        } else if (password.length < 7) {
            msg.text = "**Password must contain at least 8 characters**"
            return false
        } else if (username?.length?.let { it < 5 } == true) {
            msg.text = "**username must contain at least 5 characters**"
            return false
        } else if (!isUsernameValid(username, msg)) {
            msg.text = "**username already exists**"
            return false
        } else {
            return true
        }
    }

    private fun isUsernameValid(username : String, tv : TextView) : Boolean {
        val userList: ArrayList<String> = ArrayList()
        var bool = false

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    val childUsername = childSnapshot.key as String
                    if (childUsername == username) {
                        bool = true
                        break
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error if necessary
            }
        }
        database.addValueEventListener(valueEventListener)
        database.addListenerForSingleValueEvent(valueEventListener)

        return bool
    }

}

