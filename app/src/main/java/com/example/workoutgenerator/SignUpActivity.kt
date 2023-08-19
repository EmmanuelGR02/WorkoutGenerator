
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
import java.util.Date

data class SignUpActivity(val name : String? = null, val lastName : String? = null, val username : String? = null, val password : String? = null, val birthdate : String? = null, val gender : String? = null)  {

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
            Database.getInstance().isUsernameValid(username) { isUsernameValid ->
                if (isUsernameValid) {
                    msg.text = "**username already exists**"
                }
                // Callback with the final validation result
                callback(!isUsernameValid)
            }
        }
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SignUpActivity
        if (username != other.username) return false
        return true
    }
    override fun hashCode(): Int {
        return username?.hashCode() ?: 0
    }
}

