
package com.example.workoutgenerator

import com.example.workoutgenerator.R
import com.example.workoutgenerator.databinding.ProfileLayoutBinding
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


val database = FirebaseDatabase.getInstance().reference
var currentUser = ""
class MainActivity : ComponentActivity() {
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
        var selectedGender = ""
        var selectedDateString = ""

        val birthdateBtn = findViewById<Button>(R.id.selectBirthdateButton)
        birthdateBtn.setOnClickListener {
            showDatePickerDialog { selectedDate ->
                val calendar = Calendar.getInstance()
                calendar.time = selectedDate

                selectedDateString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
                birthdateBtn.text = selectedDateString
            }
        }

        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Get the selected gender from the adapter using the position
                selectedGender = genderOptions[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        signUpButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.signUp_name).text.toString()
            val lastName = findViewById<EditText>(R.id.signUp_lastName).text.toString()

            val username = findViewById<EditText>(R.id.signUp_username).text.toString()
            val password = findViewById<EditText>(R.id.signUp_password).text.toString()
            val reEnteredPswd = findViewById<EditText>(R.id.signUp_reEnteredPassword).text.toString()
            val signUpErrMessage = findViewById<TextView>(R.id.signUp_errMessage)

            val userInfo = SignUpActivity(name, lastName, username, password, selectedDateString,selectedGender)

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

    @SuppressLint("MissingInflatedId")
    private fun signIn() {
        val username = findViewById<EditText>(R.id.logIn_username).text.toString()
        val password = findViewById<EditText>(R.id.logIn_password).text.toString()
        val errMessage = findViewById<TextView>(R.id.signIn_errMessage)
        val si = LoginActivity(username, password)
        currentUser = username
        // Check login credentials directly using Database.getInstance()
        si.isLoginValid(errMessage) { isValid ->
            if (isValid) {
                // Retrieve the user name using Database.getInstance()
                setContentView(R.layout.welcome_layout)
                val welcome = findViewById<TextView>(R.id.welcome_message)
                val getStartedBtn = findViewById<Button>(R.id.getStartedBtn)
                val welcomeBackBtn = findViewById<Button>(R.id.welcome_backButton)
                goBack(welcomeBackBtn)
                // welcome the user and check if today its their birthday
                User(username).welcomeText { text ->
                    welcome.text = text
                }
                getStartedBtn.setOnClickListener {
                    setContentView(R.layout.profile_layout)
                    openActivities()
                }
            }
        }
    }

    private fun openWorkoutActivity() {
        val workoutBtn = findViewById<Button>(R.id.workoutBtn)

        workoutBtn.setOnClickListener {
            val workoutIntent = Intent(this, WorkoutActivity::class.java)
            startActivity(workoutIntent)
            openActivities()
        }
    }

    private fun openProfileActivity() {
        val profileBtn = findViewById<Button>(R.id.profileBtn)

        profileBtn.setOnClickListener {
            val profileIntent = Intent(this, ProfileActivity::class.java)
            startActivity(profileIntent)
            openActivities()
        }
    }

    fun openActivities() {
        openWorkoutActivity()
        openProfileActivity()
        openFriendsActivity()
    }

    private fun openFriendsActivity() {
        val friendsBtn = findViewById<Button>(R.id.friendsBtn)

        friendsBtn.setOnClickListener {
            val friendsIntent = Intent(this, FriendsActivity::class.java)
            startActivity(friendsIntent)
            openActivities()
        }
    }

    fun showDatePickerDialog(callback: DateCallback) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDateCalendar = Calendar.getInstance()
                selectedDateCalendar.set(Calendar.YEAR, year)
                selectedDateCalendar.set(Calendar.MONTH, month)
                selectedDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val selectedDate = selectedDateCalendar.time
                callback(selectedDate)
            },
            currentYear,
            currentMonth,
            currentDay
        )
        datePickerDialog.show()
    }


    // Back to Log In Page
    private fun goBack(button: Button) {
        button.setOnClickListener {
            logIn()
        }
    }

}

typealias DateCallback = (Date) -> Unit
