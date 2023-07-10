package com.example.workoutgenerator

import com.google.firebase.database.FirebaseDatabase

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

}