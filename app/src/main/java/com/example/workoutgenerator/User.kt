package com.example.workoutgenerator

import android.util.Log
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

    fun getLastname(callback: (lastname: String?) -> Unit) {
        database.getUserInfo(username.toString(), "user info") { snapshot ->
            val lastname = snapshot?.child("lastName")?.value?.toString() ?: "N/A"
            callback(lastname)
        }
    }

    fun getBirthdate(callback: (birthdate: String) -> Unit) {
        database.getUserInfo(username.toString(), "user info") { snapshot ->
            val birthdate = snapshot?.child("birthDate")?.value?.toString() ?: "N/A"
            callback(birthdate)
        }
    }

    // check if the current day is the users birthdate
    private fun isBirthday(callback: (Boolean) -> Unit) {
        getBirthdate { birthdate ->
            if (birthdate == "N/A") {
                callback(false) // If birthdate is not available, not the user's birthday
            } else {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val birthDate = sdf.parse(birthdate)

                val today = Calendar.getInstance()
                val birthCalendar = Calendar.getInstance()
                birthCalendar.time = birthDate

                val isTodayBirthday =
                    today.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) &&
                            today.get(Calendar.DAY_OF_MONTH) == birthCalendar.get(Calendar.DAY_OF_MONTH)

                callback(isTodayBirthday)
            }
        }
    }

    fun getAge(callback: (age: Int) -> Unit) {
        getBirthdate { birthdate ->
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

    fun welcomeText(callback: (String) -> Unit) {
        val randomInt = (1..5).random()
        getName { name ->
            isBirthday { birthday ->
                val text = if (birthday) {
                    "Happy Birthday, $name! \nHave a good one"
                } else {
                    when (randomInt) {
                        1 -> "Welcome, $name. \nWe hope that you're having a great day"
                        2 -> "Hello, $name. \nHope it's going well"
                        3 -> "What's up, $name? \nLet's Start the grind!"
                        4 -> "Hello, $name. \nHappy to see you again"
                        else -> "Welcome, $name!"
                    }
                }
                callback(text)
            }
        }
    }

    // Specifies the child path to get the wanted PR on "getPersonalRecords()"
    fun getBenchPR(callback: (benchPR: String?) -> Unit) {
        database.getUserStats(username.toString(), "bench PR", callback)
    }
    fun getSquatPR(callback: (squatPR: String?) -> Unit) {
        database.getUserStats(username.toString(),"squat PR", callback)
    }
    fun getDeadliftPR(callback: (deadliftPR: String?) -> Unit) {
        database.getUserStats(username.toString(),"deadlift PR", callback)
    }
    fun getWeight(callback: (weight: String?) -> Unit) {
        database.getUserStats(username.toString(), "weight", callback)
    }
    fun getHeight(callback: (height: String?) -> Unit) {
        database.getUserStats(username.toString(), "height", callback)
    }

    // Get the last workout done by this user
    fun getLastWorkout(callback: (lastWorkout: String?) -> Unit) {
        database.getLatestWorkoutInfo(username.toString()) { snapshot ->
            val lastWorkout = snapshot?.child("workout")?.value?.toString() ?: "N/A"
            callback(lastWorkout)
        }
    }
    fun getCurrentWorkout(callback: (workout: String?) -> Unit) {
        database.getUserInfo(username.toString(), "latest workout") { snapshot ->
            val currWorkout = snapshot?.child("workout")?.value?.toString() ?: "N/A"
            callback(currWorkout)
        }
    }

    // Get a list of the users friends
    fun getFriends(callback: (ArrayList<String>) -> Unit) {
        database.getUserInfo(username.toString(), "friends") { snapshot ->
            val friendsList = ArrayList<String>()

            if (snapshot != null && snapshot.exists()) {
                for (childSnapshot in snapshot.children) {
                    val friendUsername = childSnapshot.value.toString()
                    friendsList.add(friendUsername)
                }
            }
            callback(friendsList)
        }
    }
}
