package com.example.workoutgenerator

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


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

    fun isValidInputs(): Int {

        var num: Int = 0

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                println(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    ids.add(childSnapshot.value.toString())
                }
            }
        })



        // check for errors
        val regex: Regex = ("[1234567890]").toRegex()

        if (ids.contains(username)) {
            return 2
        }

        if (name == "" || lastName == "" || username == "" || password == "") {
            return 1
        } else if (!password.contains(regex) || (!username.contains(regex))) {
            return 3
        } else {
            return 4
        }
    }
}