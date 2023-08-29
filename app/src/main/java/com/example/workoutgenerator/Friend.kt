
package com.example.workoutgenerator

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
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

    // get friends age
    fun getAge(callback: (age: Int) -> Unit) {
        User(username).getBirthdate { birthdate ->
            if (birthdate == "N/A") {
                callback(0) // If birthdate is not available, age is set to 0
            } else {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val birthDate = sdf.parse(birthdate)

                val today = Calendar.getInstance()
                val birthCalendar = Calendar.getInstance()
                birthCalendar.time = birthDate

                var age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)
                if (today.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH) ||
                    (today.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH))) {
                    age--
                }
                callback(age)
            }
        }
    }

    // Specifies the child path to get the wanted PR on "getPersonalRecords()"
    fun getBenchPR(callback: (benchPR: String?) -> Unit) {
        database.getPersonalRecords(username.toString(), "benchPR", callback)
    }
    fun getSquatPR(callback: (squatPR: String?) -> Unit) {
        database.getPersonalRecords(username.toString(),"squatPR", callback)
    }
    fun getDeadliftPR(callback: (deadliftPR: String?) -> Unit) {
        database.getPersonalRecords(username.toString(),"deadliftPR", callback)
    }

    fun getWeight(callback: (weight: String?) -> Unit) {
        database.getPersonalRecords(username.toString(), "weight", callback)
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
