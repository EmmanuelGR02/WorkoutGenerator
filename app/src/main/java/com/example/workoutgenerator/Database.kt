
package com.example.workoutgenerator

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.snapshots
import com.google.firebase.database.values
import java.security.MessageDigest
import java.util.Locale
import java.util.UUID

class Database private constructor() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

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
    fun saveLatestWorkoutInfo(username: String, workout: String, duration: String, song: String, date: String, caption: String, likes: Int, imageUrl: String?) {
        val workoutInfoMap = mapOf(
            "workout" to workout,
            "duration" to duration,
            "song" to song,
            "date" to date,
            "caption" to caption,
            "likes" to likes,
            "imageUrl" to imageUrl
        )

        database.child("users").child(username).child("latest workout").setValue(workoutInfoMap)
    }

    fun saveUserInfo(username: String, userInfo: SignUpActivity, onSuccess: () -> Unit, onFailure: () -> Unit) {
        // Hash the password before saving it
        val hashedPassword = userInfo.password?.let { hashPassword(it) }
        val userInfoWithHashedPassword = userInfo.copy(password = hashedPassword)

        // Check if the username already exists in the database
        database.child("users").child(username).child("user info").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    onFailure()
                } else {
                    database.child("users").child(username).child("user info").setValue(userInfoWithHashedPassword)
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
    fun isPasswordValid(password : String, username: String, callback : (Boolean) -> Unit) {
        database.child("users").child(username).child("user info").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pswdFromDB = snapshot.child("password").getValue(String::class.java)
                if (pswdFromDB != null) {
                    // Hash the provided password for comparison
                    val hashedPassword = hashPassword(password)

                    // Compare the hashed password with the one stored in the database
                    val isPasswordValid = hashedPassword == pswdFromDB
                    callback(isPasswordValid)
                } else {
                    // Password not found in the database
                    callback(false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }

    // Hashes the given password using SHA-256 algorithm
    private fun hashPassword(password: String): String { // Defines a private function named hashPassword which takes a String parameter named password and returns a String.
        val bytes = password.toByteArray() // Converts the password String into a byte array.
        val md = MessageDigest.getInstance("SHA-256") // Creates a MessageDigest object for SHA-256 hashing algorithm.
        val digest = md.digest(bytes) // Computes the hash of the byte array using the SHA-256 algorithm.
        return digest.fold("") { str, it -> str + "%02x".format(it) } // Converts the hash bytes into a hexadecimal string representation.
    }

    // check of the user stats child is empty
    fun isUserStatsEmpty(username: String, callback: (Boolean) -> Unit) {
        val userStatsRef = database.child("users").child(username).child("user stats")
        userStatsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    callback(true)
                } else if (!snapshot.exists()) {
                    callback(false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Database-isUserStatsEmpty", "Error")
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

    private fun removeUserFromLikedPic(username: String, likedUsername: String) {
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
    private fun getUsernamesForLikedPic(username: String, callback: (ArrayList<String>) -> Unit) {
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

    // Increase likes
    private fun increaseLike(username: String) {
        val likesRef = database.child("users").child(username).child("latest workout").child("likes")
        // Add the liked user to the list
        setUsernameForLikedPic(currentUser, username)
        // Get the current likes count
        likesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentLikes = snapshot.getValue(Long::class.java) ?: 0
                val newLikes = currentLikes + 1

                // Update likes count
                likesRef.setValue(newLikes)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled if needed
            }
        })
    }

    // Decrease likes
    private fun decrementLike(username: String) {
        val likesRef = database.child("users").child(username).child("latest workout").child("likes")

        // Remove the unliked user from the list
        removeUserFromLikedPic(currentUser, username)
        // Get the current likes count
        likesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentLikes = snapshot.getValue(Long::class.java) ?: 0

                // Ensure likes don't go negative
                if (currentLikes > 0) {
                    val newLikes = currentLikes - 1

                    // Update likes count
                    likesRef.setValue(newLikes)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Database", "decrementLike() - failed to decrement like")
            }
        })
    }

    // check for the current user is in the "liked pic username"
    // and it increase or decreases a like depending on if the friend is already in there or not
    fun setLikes(username: String) {
        getUsernamesForLikedPic(currentUser) { likedUsernamesList ->
            val hasLikes = likedUsernamesList.contains(username)

            if (!hasLikes) {
                increaseLike(username)
            } else {
                decrementLike(username)
            }
        }
    }


    // returns likes
    fun getLikes(username: String, callback: (likes: Int) -> Unit) {
        val likesRef = database.child("users").child(username).child("latest workout").child("likes")

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

    // send friend request logic
    fun sendFriendRequest(username: String) {
        val requestsRef = database.child("users").child(username).child("friend requests")

        // Retrieve the current list of friend requests for the target user
        requestsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val requestsList = if (snapshot.exists()) {
                    // If the friend requests node exists, retrieve the existing list
                    snapshot.value as? ArrayList<String> ?: ArrayList()
                } else {
                    // If the friend requests node doesn't exist, create a new list
                    ArrayList()
                }

                // Check if the current user is not already in the list
                if (!requestsList.contains(currentUser)) {
                    // Add the current user to the list
                    requestsList.add(currentUser)

                    // Update the friend requests in the database
                    requestsRef.setValue(requestsList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Database", "sendFriendRequest() - Fail to send friend request")
            }
        })
    }

    // remove user from the request list when the user is accepted as friend or denied
    fun removeRequest(username: String) {
        getRequests(currentUser) { requestsList ->
            if (requestsList.contains(username)) {
                // Remove the user to be removed from the requests list
                requestsList.remove(username)

                // Update the friend requests in the database with the modified list
                val requestRef = database.child("users").child(currentUser).child("friend requests")
                requestRef.setValue(requestsList)
            }
        }
    }

    // set workout generated
    fun setTempGeneratedWorkout(username : String, workoutList : List<String>) {
        val requestRef = database.child("users").child(username).child("temp workout")
        val workoutInfoMap = mapOf(
            "workout" to workoutList,
        )
        requestRef.setValue(workoutInfoMap)
    }

    // get temporary generated workout
    fun getTempGenerateWorkout(username : String, callback : (tempWorkout : List<String>) -> Unit) {
        val requestRef = database.child("users").child((username)).child("temp workout").child("workout")

        requestRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val requestList = ArrayList<String>()

                for (childSnapshot in snapshot.children) {
                    val workout = childSnapshot.value.toString()
                    requestList.add(workout)
                }
                callback(requestList)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Database-getTempGeneratedWorkout", "Error returning temporary generated workout")
            }
        })
    }

    //returns the list of users that have sent a friend request to the current user
    fun getRequests(username: String, callback: (usernames: ArrayList<String>) -> Unit) {
        val requestRef = database.child("users").child(username).child("friend requests")

        requestRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val requestsList = ArrayList<String>()

                for (childSnapshot in snapshot.children) {
                    val requestUsername = childSnapshot.value.toString()
                    requestsList.add(requestUsername)
                }
                callback(requestsList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Database-getRequests", "Error returning friend requests")
            }
        })
    }

    // add friend
    fun addFriend(username: String) {
        val friendsRef = database.child("users").child(currentUser).child("friends")

        // Get the current list of friends
        User(currentUser).getFriends { friends ->
            // Add the new username to the list if it's not already there
            if (!friends.contains(username)) {
                friends.add(username)
                // Update the friends list in the database with the modified list
                friendsRef.setValue(friends)
            }
        }
    }

    // remove friend
    fun removeFriend(username: String) {
        val friendsRef = database.child("users").child(currentUser).child("friends")

        User(currentUser).getFriends { friends ->
            if (friends.contains(username)) {
                friends.remove(username)
                friendsRef.setValue(friends)
            }
        }
    }

    // Function to add the the user that the friend request was sent to, to the current users friends whenever the other user accepts the friend request
    fun addCurrUserToRequested(username: String) {
        val requestedFriendsRef = database.child("users").child(username).child("friends")

        User(username).getFriends { friends ->
            if (!friends.contains(currentUser)) {
                friends.add(currentUser)
                requestedFriendsRef.setValue(friends)
            }
        }
    }

    // If the current user removes a friend. this function will remove the current user from the other user's friends list
    fun removeCurrUserFromOtherUserFriends(username: String) {
        val otherUsersFriendsRef = database.child("users").child(username).child("friends")

        User(username).getFriends { friends ->
            if (friends.contains(currentUser)) {
                friends.remove(currentUser)
                otherUsersFriendsRef.setValue(friends)
            }
        }
    }

}
