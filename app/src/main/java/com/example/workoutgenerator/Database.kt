
package com.example.workoutgenerator

import android.provider.ContactsContract.Data
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.Calendar
import java.util.Locale

class Database private constructor() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun getDatabase(): DatabaseReference {
        return database
    }

    // Singleton pattern for database instance
    companion object {
        @Volatile
        private var instance: Database? = null

        fun getInstance(): Database {
            return instance ?: synchronized(this) {
                instance ?: Database().also { instance = it }
            }
        }
    }

    // saves the info for the latest workout
    fun saveLatestWorkoutInfo(username: String, workout: String, duration: String, song: String, date: String, caption: String) {
        val workoutInfoMap = mapOf(
            "workout" to workout,
            "duration" to duration,
            "song" to song,
            "date" to date,
            "caption" to caption
        )

        database.child("users").child(username).child("latest workout").setValue(workoutInfoMap)
    }

    fun saveUserInfo(username: String, userInfo: SignUpActivity, onSuccess: () -> Unit, onFailure: () -> Unit) {
        // Check if the username already exists in the database
        database.child("users").child(username).child("user info").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    onFailure()
                } else {
                    database.child("users").child(username).child("user info").setValue(userInfo)
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener {
                            onFailure()
                        }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                onFailure()
            }
        })
    }

    // Check if given username exists in the database
    fun isUsernameValid(username: String, callback: (Boolean) -> Unit) {
        database.child("users").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val arr = ArrayList<String>()
                for (i in p0.children) {
                    val user = i.child("username").value
                    arr.add(user.toString())
                }
                val isUsernameValid = arr.contains(username)
                callback(isUsernameValid)
            }

            override fun onCancelled(p0: DatabaseError) {
                callback(false)
            }
        })
    }

    // Check if given password matches with the username
    fun isPasswordValid(password : String,username: String, callback : (Boolean) -> Unit) {
        database.child("users").child(username).child("user info").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val arr = ArrayList<String>()
                val pswd = snapshot.child("password").value.toString()
                arr.add(pswd)

                val isPasswordValid = arr.contains(password)
                callback(isPasswordValid)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }

    // this method can be called when any personal info needs to be retrieved. (ex. name, lastname, gender, password, birth date)
    fun getUserInfo(username: String, path: String, callback: (DataSnapshot?) -> Unit) {
        database.child("users").child(username).child(path).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    fun getLatestWorkoutInfo(username: String, callback: (DataSnapshot?) -> Unit) {
        database.child("users").child(username).child("latest workout").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    // Save the stats of the user
    fun saveUserStats(username: String, benchPR: String?, squatPR: String?, deadliftPR: String?, weight: String?, height: String?) {
        val userStatsMap = mapOf(
            "benchPR" to benchPR,
            "squatPR" to squatPR,
            "deadliftPR" to deadliftPR,
            "weight" to weight,
            "height" to height
        )
        database.child("users").child(username).child("user stats").setValue(userStatsMap)
    }

    // Get the personal record wanted from the database
    fun getUserStats(username: String, key: String, callback: (record: String?) -> Unit) {
        database.child("users").child(username).child("user stats").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val record = snapshot.child(key).value?.toString()
                callback(record)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }
}
