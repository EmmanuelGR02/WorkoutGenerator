
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

    fun getUsername() : String {
        return this.username.toString()
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

    // Get info needed from the latest workout
    fun getLatestWorkoutInfo(info: String, callback: (data: String?) -> Unit) {
        database.getLatestWorkoutInfo(username.toString()) { snapshot ->
            val data = snapshot?.child(info)?.value?.toString() ?: "N/A"
            callback(data)
        }
    }


    // Get a list of the friend's friends
    fun getFriends(callback: (Boolean) -> Unit) : ArrayList<String>? {
        return null
    }
}
