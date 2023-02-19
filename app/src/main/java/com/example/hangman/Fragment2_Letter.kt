package com.example.hangman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class ChooseLetterFragment : Fragment() {
    private lateinit var letterButtons: Array<Button>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_choose_letter, container, false)
        letterButtons = arrayOf(
            view.findViewById(R.id.button_a),
            view.findViewById(R.id.button_b),
            view.findViewById(R.id.button_c),
            // and so on for all the letter buttons
        )
        for (button in letterButtons) {
            button.setOnClickListener { selectLetter(button) }
        }
        return view
    }

    private fun selectLetter(button: Button) {
        // Disable the button to prevent selecting it again
        button.isEnabled = false
        // Pass the selected letter to the main game fragment
        val gameFragment = parentFragment as GameFragment
        gameFragment.checkLetter(button.text.toString())
    }
}