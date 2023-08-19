
package com.example.workoutgenerator

import com.example.workoutgenerator.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

//val database = FirebaseDatabase.getInstance().reference
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.nav_container, SignInFragment()).commit()
    }

    // Back to Log In Page
    fun backToSignIn(button: Button) {
        button.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.nav_container, SignInFragment()).commit()
        }
    }

}
