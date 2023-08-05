package com.example.workoutgenerator

import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class User(private val username : String? = null) {
    private val database : Database = Database.getInstance()

    fun getName(callback: (name: String?) -> Unit) {
        database.getUserInfo(username.toString(), "user info") { snapshot ->
            val name = snapshot?.child("name")?.value?.toString() ?: "N/A"
            callback(name)
        }
    }

    fun getAge(callback: (age: Int) -> Unit) {
        database.getUserInfo(username.toString(), "user info") { snapshot ->
            val birthdate = snapshot?.child("birthdate")?.value?.toString() ?: "N/A"
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

    fun addFriend(friend: String) {
        database.getUserInfo(username.toString(), "friends") { snapshot ->
            val friendsList = mutableListOf<String>()

            if (snapshot != null && snapshot.exists()) {
                for (childSnapshot in snapshot.children) {
                    val friendUsername = childSnapshot.value.toString()
                    friendsList.add(friendUsername)
                }
            }
            friendsList.add(friend)
            Database.getInstance().getDatabase().child("users").child(username.toString()).child("friends").setValue(friendsList)
        }
    }

    // Gets PRs wanted from database
    private fun getPersonalRecords(key: String, callback: (record: String?) -> Unit) {
        database.getUserInfo(username.toString(), "personal records") { snapshot ->
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

    // Get the last workout done by this user
    fun getLastWorkout(callback: (lastWorkout: String?) -> Unit) {
        database.getUserWorkout(username.toString()) { snapshot ->
            val lastWorkout = snapshot?.child("workout")?.value?.toString() ?: "N/A"
            callback(lastWorkout)
        }
    }

    // Get a list of the users friends
    fun getFriends(callback: (Boolean) -> Unit) : ArrayList<String>? {
        return null
    }
}