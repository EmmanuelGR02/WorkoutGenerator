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

    fun addFriend(friend: String) {
        val friendsList =   ArrayList<String>()

        val a = "a"
        val b = "b"
        val c = "c"
        val d = "d"

        friendsList.add(a)
        friendsList.add(b)
        friendsList.add(c)
        friendsList.add(d)

        Database.getInstance().isUsernameValid(friend) {isValid ->
            if (isValid) {
                Log.e("User", "Username is not valid")

            } else {
                database.getUserInfo(username.toString(), "friends") { snapshot ->

                    if (snapshot != null && snapshot.exists()) {
                        for (childSnapshot in snapshot.children) {
                            val friendUsername = childSnapshot.value.toString()
                            friendsList.add(friendUsername)
                        }
                    }
                    friendsList.add(friend)
                    Database.getInstance().getDatabase().child("users").child(username.toString()).child("friends").setValue(friendsList)
                }
                Log.e("User", friendsList.toString())
            }
        }
    }

    fun unAddFriend(friend: String) {
        database.getUserInfo(username.toString(), "friends") { snapshot ->

        }
    }

    fun getBirthdate(callback: (birthdate: String) -> Unit) {
        database.getUserInfo(username.toString(), "user info") { snapshot ->
            val birthdate = snapshot?.child("birthdate")?.value?.toString() ?: "N/A"
            callback(birthdate)
        }
    }

    fun isBirthday(callback: (Boolean) -> Unit) {
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
        database.getPersonalRecords(username.toString(), "bench PR", callback)
    }
    fun getSquatPR(callback: (squatPR: String?) -> Unit) {
        database.getPersonalRecords(username.toString(),"squat PR", callback)
    }
    fun getDeadliftPR(callback: (deadliftPR: String?) -> Unit) {
        database.getPersonalRecords(username.toString(),"deadlift PR", callback)
    }

    // Get the last workout done by this user
    fun getLastWorkout(callback: (lastWorkout: String?) -> Unit) {
        database.getLatestWorkoutInfo(username.toString()) { snapshot ->
            val lastWorkout = snapshot?.child("workout")?.value?.toString() ?: "N/A"
            callback(lastWorkout)
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
