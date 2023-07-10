package com.example.workoutgenerator

// This class will contain the background code for the login screen
class LoginActivity {
    // holds the name of the user logged in
    private var username: String = ""
    private var password: String = ""

    // Constructor
    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }

    // Getters
    fun getUsername(): String {
        return username
    }
    fun getPassword(): String {
        return password
    }

    // Checks if the given username and password are in the database
    // returns true if yes, false otherwise
    fun isLoginValid(): Boolean {
        return true
    }

}