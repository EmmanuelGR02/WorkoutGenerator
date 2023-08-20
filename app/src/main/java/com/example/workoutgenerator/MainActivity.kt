
package com.example.workoutgenerator


import com.example.workoutgenerator.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.fragment.app.Fragment

//val database = FirebaseDatabase.getInstance().reference
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.nav_container, SignInFragment()).commit()
    }

    fun fragmentReplace(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction?.replace(R.id.nav_container, fragment)?.commit()
    }

    fun navigate(friends: Button, profile: Button, workout: Button) {
        val friendsFragment = FriendsFragment()
        val profileFragment = ProfileFragment()
        val workoutFragment = WorkoutGeneratorFragment()

        friends.setOnClickListener {
            fragmentReplace(friendsFragment)
        }
        profile.setOnClickListener {
            fragmentReplace(profileFragment)
        }
        workout.setOnClickListener {
            fragmentReplace(workoutFragment)
        }
    }

}
