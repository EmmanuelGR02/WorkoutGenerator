package com.example.workoutgenerator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WorkoutActivity : OpenActivities() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workout_layout)

        val profileBtn = findViewById<Button>(R.id.profileBtn)
        profileBtn.setOnClickListener {
            openProfileActivity()
        }

        val friendsBtn = findViewById<Button>(R.id.friendsBtn)
        friendsBtn.setOnClickListener {
            openFriendsActivity()
        }
    }
}