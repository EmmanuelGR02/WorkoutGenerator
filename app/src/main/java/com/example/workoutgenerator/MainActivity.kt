
package com.example.workoutgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.nav_container, SignInFragment()).commit()

        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }

        val python = Python.getInstance()
        val pyObj = python.getModule("SpotifyAPI")



    }

    fun navigateToFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_container, fragment).commit()
    }
}
