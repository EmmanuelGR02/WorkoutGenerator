package com.example.workoutgenerator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


class MusicFragment : Fragment() {

    private var isPlaying: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.musictab_layout, container, false)

        // Get a reference to the play button ImageView
        val playButton = view.findViewById<ImageView>(R.id.play_button)
        val text = view.findViewById<TextView>(R.id.song_name)

        // Set OnClickListener on the play button ImageView
        playButton.setOnClickListener {
            // Toggle the play/pause state
            isPlaying = !isPlaying
            // Change the button image based on the play/pause state
            if (isPlaying) {
                playButton.setImageResource(R.drawable.songpausebutton_icon)
            } else {
                playButton.setImageResource(R.drawable.playbutton_icon)
            }
        }
        text.text = "Apocalypse"

        return view
    }
}
