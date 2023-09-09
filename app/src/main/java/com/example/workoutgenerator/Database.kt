
package com.example.workoutgenerator

import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.compose.animation.core.snap
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
        setLastWorkoutDate(username)
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

    //get all the users in the database
    fun getAllUsers(callback: (ArrayList<String>) -> Unit) {
        val userList = ArrayList<String>()

        database.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    val username = userSnapshot.key
                    username?.let {
                        userList.add(it)
                    }
                }
                callback(userList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun setUsernameForLikedPic(username: String, likedUsername: String) {
        getUsernamesForLikedPic(username) { likedUsernamesList ->
            // Check if the likedUsername is already in the list
            if (!likedUsernamesList.contains(likedUsername)) {
                // If not, add it to the list
                likedUsernamesList.add(likedUsername)

                // Update the liked pic usernames in the database
                val usernameRef = database.child("users").child(username).child("liked pic usernames")
                usernameRef.setValue(likedUsernamesList)
            }
        }
    }

    fun removeUserFromLikedPic(username: String, likedUsername: String) {
        getUsernamesForLikedPic(username) { likedUsernamesList ->
            // Check if the likedUsername is in the list
            if (likedUsernamesList.contains(likedUsername)) {
                // If it is, remove it from the list
                likedUsernamesList.remove(likedUsername)

                // Update the liked pic usernames in the database
                val usernameRef = database.child("users").child(username).child("liked pic usernames")
                usernameRef.setValue(likedUsernamesList)
            }
        }
    }




    // returns the list of usernames which the current user has liked their friends workout pic
    fun getUsernamesForLikedPic(username: String, callback: (ArrayList<String>) -> Unit) {
        val usernameRef = database.child("users").child(username).child("liked pic usernames")
        val friendsList = ArrayList<String>()

        usernameRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) { // Check for existence
                    for (childSnapshot in snapshot.children) {
                        val friendUsername = childSnapshot.value.toString()
                        friendsList.add(friendUsername)
                    }
                }
                callback(friendsList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    // increase likes
    fun increaseLike(username: String) {
        val likesRef = database.child("users").child(username).child("likes")

        // Get the current likes count
        likesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val currentLikes = snapshot.value as Long
                    val newLikes = currentLikes + 1
                    likesRef.setValue(newLikes)
                } else {
                    // If the likes node doesn't exist, create it with a value of 1
                    likesRef.setValue(1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled if needed
            }
        })
    }


    // decrease likes
    fun decrementLike(username: String) {
        val likesRef = database.child("users").child(username).child("likes")

        // Get the current likes count
        likesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val currentLikes = snapshot.value as Long
                    if (currentLikes > 0) {
                        val newLikes = currentLikes - 1
                        likesRef.setValue(newLikes)
                    }
                    // You might want to handle the case where likes are already zero
                } else {
                    // If the likes node doesn't exist, create it with a value of 0
                    likesRef.setValue(0)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled if needed
            }
        })
    }

    // check for the current user is in the "liked pic username"
    // and it increase or decreases a like depending on if the friend is already in there or not
    fun setLikes(username: String) {
        getUsernamesForLikedPic(currentUser) { likedUsernamesList ->
            val hasLikes = likedUsernamesList.contains(username)
            val likesRef = database.child("users").child(username).child("likes")

            // Check if the user has any likes, if not, set their current likes to 0
            likesRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        // Set current likes to 0
                        likesRef.setValue(0)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

            if (!hasLikes) {
                setUsernameForLikedPic(currentUser, username)
                increaseLike(username)

            } else {
                removeUserFromLikedPic(currentUser, username)
                decrementLike(username)
            }
        }
    }


    // returns likes
    fun getLikes(username: String, callback: (likes: Int) -> Unit) {
        val likesRef = database.child("users").child(username).child("likes")

        likesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val likes = snapshot.value as Long
                    callback(likes.toInt())
                } else {
                    // If the likes node doesn't exist, you can handle it accordingly, e.g., return 0 or an error.
                    callback(0)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled if needed
            }
        })
    }

    // returns the date of the previous workout
    fun getLastWorkoutDate(username: String, callback: (date: String) -> Unit) {
        database.child("users").child(username).child("last workout date").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val date = snapshot.value?.toString() ?: "N/A"
                callback(date)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    // returns the date of the current friend workout
    fun getCurrentWorkoutDate(username: String, callback: (date: String) -> Unit) {
        database.child("users").child(username).child("last workout").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val date = snapshot.child("date").value?.toString() ?: "N/A"
                callback(date)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    // save the date of the previous workout
    fun setLastWorkoutDate(username: String) {
        getLatestWorkoutInfo(username) { snapshot ->
            val data = snapshot?.child("date")?.value?.toString() ?: "N/A"
            database.child("users").child(username).child("last workout date").setValue(data)
        }
    }
}
