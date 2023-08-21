
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

    // Specifies the child path to get the wanted PR on "getPersonalRecords()"
    fun getBenchPR(callback: (benchPR: String?) -> Unit) {
        database.getPersonalRecords(username.toString(), "bench PR", callback)
    }
    fun getSquatPR(callback: (squatPR: String?) -> Unit) {
        database.getPersonalRecords(username.toString(),"squat PR", callback)
    }
    fun getDeadliftPR(callback: (deadliftPR: String?) -> Unit) {
        database.getPersonalRecords(username.toString(),"deadlift PR", callback)
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
