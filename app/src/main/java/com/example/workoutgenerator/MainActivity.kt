package com.example.workoutgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.firebase.database.FirebaseDatabase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("TESTING")

    }
}
