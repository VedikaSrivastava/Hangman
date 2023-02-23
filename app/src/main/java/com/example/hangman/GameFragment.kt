package com.example.hangman

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


private const val TAG = "CrimeListFragment"
class GameFragment : Fragment() {


    private lateinit var wordTextView: TextView
    private lateinit var remainingTextView: TextView
    private lateinit var hangmanImageView: ImageView
    private lateinit var letters: List<String>
    private lateinit var chosenWord: String
    private var guessedLetters: MutableSet<String> = mutableSetOf()
    private var remainingTurns = 6

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        wordTextView = view.findViewById(R.id.text_view_word)
        remainingTextView = view.findViewById(R.id.text_view_remaining)
        hangmanImageView = view.findViewById(R.id.image_view_hangman)

        // Load the word list and choose a random word
        letters = resources.getStringArray(R.array.words).toList()
        chosenWord = chooseWord()
        println("--------------------- chosenWord: "+ chosenWord)

        // Initialize the guessed letters set
        guessedLetters = mutableSetOf()
        println("--------------------- guessedLetters: "+ guessedLetters)

        // Display the initial state of the game
        updateWordView()
        updateRemainingView()

        return view
    }

    fun checkLetter(letter: String) {
        // Add the guessed letter to the set and update the display
        guessedLetters += letter
        println("--------------------- guessedLetters: "+ guessedLetters)
        updateWordView()

        // Check if the game is won or lost
        if (isWordGuessed()) {
            Toast.makeText(requireContext(), "You won!", Toast.LENGTH_SHORT).show()
        } else if (remainingTurns == 0) {
            Toast.makeText(requireContext(), "You lost! The word was $chosenWord.", Toast.LENGTH_SHORT).show()
        }
    }

    fun useTurn() {
        remainingTurns--
        updateRemainingView()
        if (remainingTurns == 0) {
            hangmanImageView.setImageResource(R.drawable.hangman6)
        } else {
            val resourceId = resources.getIdentifier("hangman${6 - remainingTurns}", "drawable", requireContext().packageName)
            hangmanImageView.setImageResource(resourceId)
        }
    }

    fun showHint(hint: String) {
        Toast.makeText(requireContext(), hint, Toast.LENGTH_SHORT).show()
    }

    fun getRemainingLetters(): Set<String> {
        return letters.toSet() - guessedLetters
    }

    private fun chooseWord(): String {
        val words = resources.getStringArray(R.array.words)
        return words.random().toLowerCase()
    }

    private fun isWordGuessed(): Boolean {
        return chosenWord.all { guessedLetters.contains(it.toString()) }
    }

    private fun updateWordView() {
        val wordView = StringBuilder()
        for (letter in chosenWord) {
            if (guessedLetters.contains(letter.toString())) {
                wordView.append(letter)
                wordView.append(' ')
            } else {
                wordView.append("_ ")
            }
        }
        wordTextView.text = wordView.toString()
        println("--------------------- wordTextView: "+ wordView.toString())
    }

    private fun updateRemainingView() {
        val remainingView = "Remaining turns: $remainingTurns"
        remainingTextView.text = remainingView
        println("--------------------- remainingTextView: "+ remainingView)

    }
}
