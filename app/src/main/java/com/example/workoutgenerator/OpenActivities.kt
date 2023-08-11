package com.example.workoutgenerator

import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

abstract class OpenActivities : AppCompatActivity() {

    protected fun openWorkoutActivity() {
        val workoutIntent = Intent(this, WorkoutActivity::class.java)
        startActivity(workoutIntent)
    }

    protected fun openProfileActivity() {
        val profileIntent = Intent(this, ProfileActivity::class.java)
        startActivity(profileIntent)
    }

    protected fun openFriendsActivity() {
        val friendsIntent = Intent(this, FriendsActivity::class.java)
        startActivity(friendsIntent)
    }
}