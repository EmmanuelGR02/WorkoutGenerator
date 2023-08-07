
package com.example.workoutgenerator

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.CountDownTimer
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
class MainActivity : ComponentActivity() {
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var database: Database
    private lateinit var slideInRightAnimation: Animation
    private lateinit var slideOutLeftAnimation: Animation

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
        // Check login credentials directly using Database.getInstance()
        si.isLoginValid(errMessage) { isValid ->
            if (isValid) {
                // Retrieve the user name using Database.getInstance()
                Database.getInstance().getUserInfo(username, "user info") { snapshot ->
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
                        main(R.layout.profile_layout)
                    }
                }
            } else {
                errMessage.text = "Invalid Credentials"
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG).show()
            }
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

    private fun main(layout: Int) {
        setContentView(layout)

        slideInRightAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        slideOutLeftAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left)

        val friendsBtn = findViewById<Button>(R.id.friendsBtn)
        val workoutBtn = findViewById<Button>(R.id.workoutBtn)
        val profileBtn = findViewById<Button>(R.id.profileBtn)
        val settingsBtn = findViewById<Button>(R.id.settingsBtn)

        friendsBtn.setOnClickListener {
            it.startAnimation(slideOutLeftAnimation)
            setContentView(R.layout.friends_layout)
            main(R.layout.friends_layout)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        workoutBtn.setOnClickListener {
            it.startAnimation(slideOutLeftAnimation)
            setContentView(R.layout.workout_layout)
            main(R.layout.workout_layout)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        profileBtn.setOnClickListener {
            it.startAnimation(slideOutLeftAnimation)
            setContentView(R.layout.profile_layout)
            main(R.layout.profile_layout)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        settingsBtn.setOnClickListener {
            it.startAnimation(slideOutLeftAnimation)
            setContentView(R.layout.settings_layout)
            main(R.layout.settings_layout)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
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
            }
            override fun onFinish() {
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel the timer if the activity is destroyed to avoid memory leaks
        countDownTimer.cancel()
    }
}

typealias DateCallback = (Date) -> Unit
