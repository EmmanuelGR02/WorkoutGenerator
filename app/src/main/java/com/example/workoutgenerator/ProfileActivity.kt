package com.example.workoutgenerator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : OpenActivities() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_layout)

        val friendsBtn = findViewById<Button>(R.id.friendsBtn)
        friendsBtn.setOnClickListener {
            openFriendsActivity()
        }

        val workoutBtn = findViewById<Button>(R.id.workoutBtn)
        workoutBtn.setOnClickListener {
            openWorkoutActivity()
        }
    }
}