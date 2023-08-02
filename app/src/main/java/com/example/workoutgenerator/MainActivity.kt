
package com.example.workoutgenerator

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

val database = FirebaseDatabase.getInstance().reference
class MainActivity : ComponentActivity() {
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var birthdateTextView: TextView
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Database.getInstance()
        logIn()
    }

    private fun logIn() {
        setContentView(R.layout.login_layout)
        val signUpButton = findViewById<Button>(R.id.logIn_signUpBtn)
        val signInButton = findViewById<Button>(R.id.signIn_btn)

        signUpButton.setOnClickListener {
            setContentView(R.layout.signup_layout)
            signUp()
        }

        signInButton.setOnClickListener {
            signIn()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun signUp() {
        val backBtn = findViewById<Button>(R.id.signUp_backButton)
        goBack(backBtn)
        val signUpButton = findViewById<Button>(R.id.signUp_button)
        val genderSpinner = findViewById<Spinner>(R.id.gender)

        val genderOptions = arrayOf("gender", "MALE", "FEMALE")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        genderSpinner.adapter = adapter

        signUpButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.signUp_name).text.toString()
            val lastName = findViewById<EditText>(R.id.signUp_lastName).text.toString()
            val username = findViewById<EditText>(R.id.signUp_username).text.toString()
            val password = findViewById<EditText>(R.id.signUp_password).text.toString()
            val reEnteredPswd = findViewById<EditText>(R.id.signUp_reEnteredPassword).text.toString()
            val signUpErrMessage = findViewById<TextView>(R.id.signUp_errMessage)

            val userInfo = SignUpActivity(name, lastName, username, password)

            userInfo.isValidInputs(signUpErrMessage, reEnteredPswd) { isValid ->
                if (isValid) {
                    database.saveUserInfo(username, userInfo,
                        onSuccess = {
                            // Clears the text from the input boxes
                            findViewById<EditText>(R.id.signUp_name).text.clear()
                            findViewById<EditText>(R.id.signUp_lastName).text.clear()
                            findViewById<EditText>(R.id.signUp_username).text.clear()
                            findViewById<EditText>(R.id.signUp_password).text.clear()
                            findViewById<EditText>(R.id.signUp_reEnteredPassword).text.clear()
                            signUpErrMessage.text = ""

                            // Shows a message that the data was saved
                            Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
                            setContentView(R.layout.login_layout)
                            logIn()
                        },
                        onFailure = {
                            Toast.makeText(this, "Username already taken", Toast.LENGTH_SHORT).show()
                        }
                    )
                } else {
                    Toast.makeText(this, "Retry Sign Up", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun signIn() {
        val username = findViewById<EditText>(R.id.logIn_username).text.toString()
        val password = findViewById<EditText>(R.id.logIn_password).text.toString()
        val errMessage = findViewById<TextView>(R.id.signIn_errMessage)
        val si = LoginActivity(username, password)
        // Check login credentials directly using Database.getInstance()
        si.isLoginValid(errMessage) { isValid ->
            if (isValid) {
                // Retrieve the user name using Database.getInstance()
                Database.getInstance().getUserInfo(username) { snapshot ->
                    val name = snapshot?.child("name")?.value?.toString() ?: "N/A"
                    setContentView(R.layout.welcome_layout)
                    val welcome = findViewById<TextView>(R.id.welcome_message)
                    welcome.text = "Welcome $name"
                    startTimer()
                }
            } else {
                errMessage.text = "Invalid Credentials"
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun showDatePickerDialog(view: android.view.View) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Update the TextView with the selected birthdate
                val selectedBirthdate = "$year-${month + 1}-$dayOfMonth"
                val birthdateBtn = findViewById<Button>(R.id.selectBirthdateButton)
                birthdateBtn.text = "$selectedBirthdate"
            },
            currentYear,
            currentMonth,
            currentDay
        )
        // Show the DatePickerDialog
        datePickerDialog.show()
    }

    // Back to Log In Page
    private fun goBack(button: Button) {
        button.setOnClickListener {
            logIn()
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
            }
            override fun onFinish() {
                setContentView(R.layout.main_layout)
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel the timer if the activity is destroyed to avoid memory leaks
        countDownTimer.cancel()
    }
}
