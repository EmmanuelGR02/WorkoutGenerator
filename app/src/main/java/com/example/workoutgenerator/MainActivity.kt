
package com.example.workoutgenerator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.ContentView
import com.example.workoutgenerator.databinding.LoginLayoutBinding
import com.example.workoutgenerator.databinding.SignupLayoutBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder
val database = FirebaseDatabase.getInstance().reference
class MainActivity : ComponentActivity() {
    private lateinit var binding: SignupLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logIn()
    }

    // Log in page functionality
    private fun logIn() {
        setContentView(R.layout.login_layout)

        // Buttons
        val signUpButton = findViewById<Button>(R.id.logIn_signUpBtn)

        signUpButton.setOnClickListener {
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

        signUpButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.signUp_name).text.toString()
            val lastName = findViewById<EditText>(R.id.signUp_lastName).text.toString()
            val username = findViewById<EditText>(R.id.signUp_username).text.toString()
            val password = findViewById<EditText>(R.id.signUp_password).text.toString()
            val reEnteredPswd = findViewById<EditText>(R.id.signUp_reEnteredPassword).text.toString()
            val signUpErrMessage = findViewById<TextView>(R.id.signUp_errMessage)

            var userInfo = SignUpActivity(name, lastName, username, password)


            var bool : Boolean = userInfo.isValidInputs(signUpErrMessage, reEnteredPswd)

            if (bool){
                database.child("users").child(username).setValue(userInfo).addOnSuccessListener {
                    // clears the text from the input boxes
                    findViewById<EditText>(R.id.signUp_name).text.clear()
                    findViewById<EditText>(R.id.signUp_lastName).text.clear()
                    findViewById<EditText>(R.id.signUp_username).text.clear()
                    findViewById<EditText>(R.id.signUp_password).text.clear()
                    findViewById<EditText>(R.id.signUp_reEnteredPassword).text.clear()

                    // pops out a message that the data was saved
                    Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Retry Sign Up", Toast.LENGTH_SHORT).show()
            }

        }
    }

    // Back to Log In Page
    private fun goBack(button: Button) {
        button.setOnClickListener {
            logIn()
        }
    }
}
