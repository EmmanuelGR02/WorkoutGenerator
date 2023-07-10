package com.example.workoutgenerator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.annotation.ContentView
import com.google.firebase.database.FirebaseDatabase

val database = FirebaseDatabase.getInstance().reference

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logIn()

    }

    // Log in page functionality
    private fun logIn() {
        setContentView(R.layout.login_layout)

        // Buttons
        val signUpButton = findViewById<Button>(R.id.logIn_signUpBtn)

        signUpButton?.setOnClickListener {
            setContentView(R.layout.signup_layout)
            // Sing up logic
            signUp()
            // Takes you back to log in page
            val backButton = findViewById<Button>(R.id.signUp_backButton)
            goBack(backButton)
        }
    }

    // store all the data given in the database
    private fun signUp() {
        val signUpButton = findViewById<Button>(R.id.signUp_button)

        signUpButton?.setOnClickListener {
            val name = findViewById<EditText>(R.id.signUp_name).text.toString()
            val lastName = findViewById<EditText>(R.id.signUp_lastName).text.toString()
            val username = findViewById<EditText>(R.id.signUp_username).text.toString()
            val password = findViewById<EditText>(R.id.signUp_password).text.toString()
            val reEnteredPswd = findViewById<EditText>(R.id.signUp_reEnteredPassword).text.toString()
            val signUpErrMessage = findViewById<TextView>(R.id.signUp_errMessage)

            val signUp = SignUpActivity(name, lastName, username, password)

            // check for bad inputs
            if (signUp.isValidInputs() == 1) {
                signUpErrMessage.text = "**Inputs cannot be left blank**"
            } else if (signUp.isValidInputs() == 2) {
                signUpErrMessage.text = "**Username already exists**"
            } else if(signUp.isValidInputs() == 3) {
                signUpErrMessage.text = "**username and password must contain at least 1 number**"
            } else if (signUp.isValidInputs() == 4) {
                if (reEnteredPswd != password) {
                    signUpErrMessage.text = "**Passwords do not match**"
                }
            }

            database.child(username).setValue(signUp)

        }
    }

    // Back to Log In Page
    private fun goBack(button: Button) {
        button.setOnClickListener {
            logIn()
        }
    }
}
