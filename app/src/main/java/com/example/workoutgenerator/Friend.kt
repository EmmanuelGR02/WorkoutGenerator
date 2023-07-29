package com.example.workoutgenerator

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import javax.security.auth.callback.Callback

class Friend(private val username : String?) {

    // Get friend name method
    fun getName(callback: (name: String?) -> Unit) {
        database.child("users").child(username.toString()).child("user info").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").value?.toString() ?: "N/A"
                callback(name)
            }

            override fun onCancelled(error : DatabaseError) {
                callback("N/A")
            }
        })
    }

    // Gets PRs wanted from database
    private fun getPersonalRecords(key: String, callback: (record: String?) -> Unit) {
        database.child("users").child(username.toString()).child("personal records").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val record = snapshot.child(key).value?.toString() ?: "N/A"
                callback(record)
            }
            override fun onCancelled(error: DatabaseError) {
                callback("N/A")
            }
        })
    }
    // Specifies the child path to get the wanted PR on "getPersonalRecords()"
    fun getBenchPR(callback: (benchPR: String?) -> Unit) {
        getPersonalRecords("bench PR", callback)
    }
    fun getSquatPR(callback: (squatPR: String?) -> Unit) {
        getPersonalRecords("squat PR", callback)
    }
    fun getDeadliftPR(callback: (deadliftPR: String?) -> Unit) {
        getPersonalRecords("deadlift PR", callback)
    }

    // Get the last workout done by this friend
    fun getLastWorkout(callback: (Boolean) -> Unit) : String {
        return ""
    }

    // Get a list of the friend's friends
    fun getFriends(callback: (Boolean) -> Unit) : ArrayList<String>? {
        return null
    }

}