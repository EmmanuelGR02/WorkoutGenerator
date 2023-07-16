
package com.example.workoutgenerator

import android.database.sqlite.SQLiteDatabase
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
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
        }

        if (isUsernameValid(username, msg)) {
            //msg.text = "**username already exists**"
            return false
        }

        return true
    }

    private fun isUsernameValid(username: String, tv: TextView): Boolean {
        var bool = true

        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val usersRef: DatabaseReference = db.getReference("users")

        val usernameExistsRef = usersRef.orderByChild("users").equalTo(username)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usernameExists = dataSnapshot.exists()
                tv.text = usernameExists.toString()
                if (usernameExists) {
                    bool = false
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error if needed
            }
        }

        usernameExistsRef.addListenerForSingleValueEvent(valueEventListener)
        //tv.text = bool.toString()
        // Return false for now, as the existence of the username will be determined asynchronously
        return bool
    }

}

