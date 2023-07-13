package com.example.workoutgenerator

import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder


// This class will get the username and password from the edit texts and
// Store the values in the database

class SignUpActivity {

    private var name: String = ""
    private var lastName: String = ""
    private var username: String = ""
    private var password: String = ""
    private  val ids = ArrayList<String>()

    constructor(name: String, lastName: String, username: String, password: String) {
        this.name = name
        this.lastName = lastName
        this.username = username
        this.password = password
    }

    fun getIds(): ArrayList<String> {
        return ids
    }

    fun isValidInputs(msg: TextView, reEnteredPswd: String): Boolean {

        // Check if username already exists in the database
        var getData = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                var sb = StringBuilder()
                for (i in p0.children) {
                    var name = i.child("name").value
                    var username = i.child("username").value.toString()
                    sb.append("${i.key}")
                }
                ids.add(sb.toString())
            }
        }
        database.addValueEventListener(getData)
        database.addListenerForSingleValueEvent(getData)

        // check for errors
        val regex: Regex = ("[1234567890]").toRegex()

        if (ids.contains(username)) {
            msg.text = "**Username already exists**"
            return false
        }else if (name == "" || lastName == "" || username == "" || password == "") {
            msg.text = "**Inputs cannot be left blank**"
            return false
        }else if (!password.contains(regex) || (!username.contains(regex))) {
            msg.text = "**username and password must contain at least 1 number**"
            return false
        }else if (reEnteredPswd != password) {
            msg.text = "**Passwords do not match**"
            return false
        }
        return true
    }
}