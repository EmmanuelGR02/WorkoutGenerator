package com.example.workoutgenerator

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.regex.Pattern


// This class will get the username and password from the edit texts and
// Store the values in the database

class SignUpActivity {

    var name: String = ""
    var lastName: String = ""
    var username: String = ""
    var password: String = ""

    constructor(name: String, lastName: String, username: String, password: String) {
        this.name = name
        this.lastName = lastName
        this.username = username
        this.password = password
    }



    fun isValidInputs(): Int {
        var num: Int = 0

        var getData = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(data: DataSnapshot) {
                for (i in data.children) {
                    if (username == i.key) {
                        num = 2
                    }
                }
            }
        }

        // check for errors
        val regex: Regex = ("[1234567890]").toRegex()

        if (name == "" || lastName == "" || username == "" || password == "") {
            return 1
        } else if(num == 2) {
            return 2
        } else if (!password.contains(regex) || (!username.contains(regex))) {
            return 3
        } else {
            return 4
        }
    }
}