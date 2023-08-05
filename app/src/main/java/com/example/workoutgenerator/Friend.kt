
package com.example.workoutgenerator

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import javax.security.auth.callback.Callback

class Friend(private val username : String?) {
    private val database : Database = Database.getInstance()

    // Get friend name method
    fun getName(callback: (name: String?) -> Unit) {
        database.getUserInfo(username.toString(), "user info") { snapshot ->
            val name = snapshot?.child("name")?.value?.toString() ?: "N/A"
            callback(name)
        }
    }

    // Gets PRs wanted from database
    private fun getPersonalRecords(key: String, callback: (record: String?) -> Unit) {
        database.getUserInfo(username.toString(), "peronal records") { snapshot ->
            val record = snapshot?.child(key)?.value?.toString() ?: "N/A"
            callback(record)
        }
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
    fun getLastWorkout(callback: (lastWorkout: String?) -> Unit) {
        database.getUserWorkout(username.toString()) { snapshot ->
            val lastWorkout = snapshot?.child("workout")?.value?.toString() ?: "N/A"
            callback(lastWorkout)
        }
    }

    // Get a list of the friend's friends
    fun getFriends(callback: (Boolean) -> Unit) : ArrayList<String>? {
        return null
    }
}