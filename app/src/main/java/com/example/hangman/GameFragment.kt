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
    private lateinit var letters: Array<String>
    var chosenWord: String = ""
    private var guessedLetters: MutableSet<String> = mutableSetOf()
    private var remainingTurns = 0
    public var hintCount = 0
    public var lettersClicked: MutableSet<String> = mutableSetOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        wordTextView = view.findViewById(R.id.text_view_word)
        remainingTextView = view.findViewById(R.id.text_view_remaining)
        hangmanImageView = view.findViewById(R.id.image_view_hangman)

        if(savedInstanceState!=null)
        {
            retrieveState(savedInstanceState)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(savedInstanceState!=null)
        {
            retrieveState(savedInstanceState)
        }
        else{
            // Load the word list and choose a random word
            letters = resources.getStringArray(R.array.words)
            chosenWord = chooseWord()
            println("--------------------- chosenWord: " + chosenWord)

            // Initialize the guessed letters set
            guessedLetters = mutableSetOf()
            println("--------------------- guessedLetters: " + guessedLetters)
            remainingTurns = 7
        }


        wordTextView = view.findViewById(R.id.text_view_word)
        remainingTextView = view.findViewById(R.id.text_view_remaining)
        hangmanImageView = view.findViewById(R.id.image_view_hangman)

        // Display the initial state of the game
        updateWordView()
        updateRemainingView()
        updateHangmanView()
    }

    fun checkLetter(letter: String) {
        // Add the guessed letter to the set and update the display
        if(chosenWord.contains(letter))
        {
            incrementRemainingTurns()
        }
        lettersClicked.add(letter)
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
            hangmanImageView.setImageResource(R.drawable.hangman8)
        }
        else {
            for (letter in chosenWord) {
                println("------------------updateWordView- letter: $letter")
                if (guessedLetters.contains(letter.toString()) == false) {
                    val resourceId = resources.getIdentifier(
                        "hangman${8 - remainingTurns}",
                        "drawable",
                        requireContext().packageName
                    )
                    hangmanImageView.setImageResource(resourceId)
                }
//                val resourceId = resources.getIdentifier("hangman${8 - remainingTurns}", "drawable", requireContext().packageName)
//                hangmanImageView.setImageResource(resourceId)
            }
        }
    }

    fun showHint(hint: String) {
        Toast.makeText(requireContext(), hint, Toast.LENGTH_SHORT).show()
    }

    fun getRemainingLetters(): Set<Any> {
        return chosenWord.toSet() - guessedLetters
    }

    private fun chooseWord(): String {
        val words = resources.getStringArray(R.array.words)
        return words.random()
    }

    private fun isWordGuessed(): Boolean {
        return chosenWord.all { guessedLetters.contains(it.toString()) }
    }

    private fun updateWordView() {
        val wordView = StringBuilder()
        for (letter in chosenWord) {
            println("------------------updateWordView- letter: $letter")
            if (guessedLetters.contains(letter.toString())) {
                wordView.append(letter)
                wordView.append(' ')
                println("------------------updateWordView- if wordView: $wordView")
            } else {
                wordView.append("_ ")
                println("------------------updateWordView- else wordView: $wordView")
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

    private fun updateHangmanView(){
        print("-------------------remainingTurns:"+remainingTurns)
        val resourceId = resources.getIdentifier(
            "hangman${8 - remainingTurns}",
            "drawable",
            requireContext().packageName
        )
        hangmanImageView.setImageResource(resourceId)
    }

    fun incrementRemainingTurns(){
        remainingTurns++
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArray("letters", letters)
        outState.putString("chosenWord",chosenWord)
        outState.putStringArrayList("guessedLetters",ArrayList(guessedLetters))
        outState.putInt("remainingTurns",remainingTurns)
        outState.putStringArrayList("lettersClicked",ArrayList(lettersClicked))
        outState.putInt("hintCount",hintCount)
        Log.d("turnsout", remainingTurns.toString())
    }

    private fun retrieveState(inState: Bundle) {
        letters = inState.getStringArray("letters") as Array<String>
        chosenWord = inState.getString("chosenWord").toString()
        guessedLetters = inState.getStringArrayList("guessedLetters")?.toMutableSet() ?:  mutableSetOf()
        remainingTurns = inState.getInt("remainingTurns")
        lettersClicked = inState.getStringArrayList("lettersClicked")?.toMutableSet() ?:  mutableSetOf()
        hintCount = inState.getInt("hintCount")
        Log.d("turns", remainingTurns.toString())
    }

    fun usedHint() {
        hintCount++
    }

}
