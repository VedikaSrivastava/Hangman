package com.example.hangman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment

class HintFragment : Fragment() {
    private lateinit var hintButton: Button
    private var hintCount = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hint, container, false)
        hintButton = view.findViewById(R.id.button_hint)
        hintButton.setOnClickListener { showHint() }
        return view
    }

    private fun showHint() {
        val gameFragment = parentFragment as GameFragment
        when (hintCount) {
            0 -> {
                // Display the hint message
                gameFragment.showHint("This is a type of fruit.")
            }
            1 -> {
                // Disable half of the remaining letter buttons
                val remainingLetters = gameFragment.getRemainingLetters()
                val disableCount = remainingLetters.size / 2
                var disabledCount = 0
                for (button in letterButtons) {
                    if (button.isEnabled && !remainingLetters.contains(button.text.toString())) {
                        button.isEnabled = false
                        disabledCount++
                        if (disabledCount >= disableCount) break
                    }
                }
                // Deduct a turn for using the hint
                gameFragment.useTurn()
            }
            2 -> {
                // Disable all vowel buttons and show the vowels
                val vowels = setOf("a", "e", "i", "o", "u")
                for (button in letterButtons) {
                    if (button.isEnabled && vowels.contains(button.text.toString())) {
                        button.isEnabled = false
                        button.alpha = 0.5f
                    }
                }
                // Deduct a turn for using the hint
                gameFragment.useTurn()
            }
            else -> {
                // Hint not available
                Toast.makeText(requireContext(), "Hint not available", Toast.LENGTH_SHORT).show()
                return
            }
        }
        hintCount++
    }
}