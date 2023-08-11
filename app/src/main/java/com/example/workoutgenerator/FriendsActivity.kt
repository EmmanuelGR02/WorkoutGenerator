package com.example.workoutgenerator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.workoutgenerator.databinding.FriendsLayoutBinding

class FriendsActivity : OpenActivities() {
    private lateinit var binding: FriendsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FriendsLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val profileBtn = findViewById<Button>(R.id.profileBtn)
        profileBtn.setOnClickListener {
            openProfileActivity()
        }

        val workoutBtn = findViewById<Button>(R.id.workoutBtn)
        workoutBtn.setOnClickListener {
            openWorkoutActivity()
        }

        val friend = binding.friend.text.toString()
        val addFriendBtn = binding.addFriend
        val friendList = binding.friendList
        val showFriendsBtn = binding.showFriends

        val user = User(currentUser)

        addFriendBtn.setOnClickListener{
            user.addFriend(friend)
        }

        showFriendsBtn.setOnClickListener {
            user.getFriends { friends ->
                friendList.text = friends.toString()
            }
        }
    }

}