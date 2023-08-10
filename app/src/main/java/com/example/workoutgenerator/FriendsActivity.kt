package com.example.workoutgenerator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.workoutgenerator.databinding.FriendsLayoutBinding

class FriendsActivity : AppCompatActivity() {
    private lateinit var binding: FriendsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FriendsLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

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