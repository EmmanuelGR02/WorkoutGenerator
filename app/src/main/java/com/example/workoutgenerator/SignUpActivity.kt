package com.example.workoutgenerator

import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
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

    fun userInfo(name: String, lastName: String) {
        this.name = name
        this.lastName = lastName
    }

    fun getIds(view: TextView){
        for (i in ids) {
            view.text = i
        }
    }

    fun isValidInputs(msg: TextView, reEnteredPswd: String) {
        val signUp = SignUpActivity(name, lastName,username, password)
        database.child("Login Info")

        // Check if username already exists in the database
        var getData = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                   ids.add(i.child("Login Info").toString())
                }
            }
        }
        database.addValueEventListener(getData)
        database.addListenerForSingleValueEvent(getData)

        // check for errors
        val regex: Regex = ("[1234567890]").toRegex()

        if (ids.contains(password)) {
            msg.text = "**Username already exists**"
        }else if (name == "" || lastName == "" || username == "" || password == "") {
            msg.text = "**Inputs cannot be left blank**"
        }else if (!password.contains(regex)) {
            msg.text = "**password must contain at least 1 number**"
        }else if (reEnteredPswd != password) {
            msg.text = "**Passwords do not match**"
        } else {
            database.child("Login Info").child(username).setValue(password)
            database.child("User Info").child(username).setValue(name)
        }
    }
}