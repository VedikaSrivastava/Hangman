package com.example.hangman

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_choose_letter.*


public class ChooseLetterFragment() : Fragment() {
    private lateinit var letterButtons: Array<Button>
    private lateinit var hintButton: Button
    private lateinit var restartButton: Button
    private lateinit var resultLayout: LinearLayout
    private lateinit var choose: LinearLayout
    private lateinit var keyboard: LinearLayout
    private lateinit var wonView: TextView
    private lateinit var lostView: TextView

    private var hintCount = 0
    private var lettersClicked: MutableSet<String> = mutableSetOf()
    private var alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    var TAG = "testing"

    var gameFragment = GameFragment()

    init {

    }
    constructor(gameFragment: GameFragment) : this() {
        this.gameFragment = gameFragment
        lettersClicked = gameFragment.lettersClicked
        hintCount = gameFragment.hintCount
    }

    private var listener: RestartListener? = null

    interface RestartListener {
        fun restartApp()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choose_letter, container, false)
        letterButtons = arrayOf(
            view.findViewById(R.id.button_a),
            view.findViewById(R.id.button_b),
            view.findViewById(R.id.button_c),
            view.findViewById(R.id.button_d),
            view.findViewById(R.id.button_e),
            view.findViewById(R.id.button_f),
            view.findViewById(R.id.button_g),
            view.findViewById(R.id.button_h),
            view.findViewById(R.id.button_i),
            view.findViewById(R.id.button_j),
            view.findViewById(R.id.button_k),
            view.findViewById(R.id.button_l),
            view.findViewById(R.id.button_m),
            view.findViewById(R.id.button_n),
            view.findViewById(R.id.button_o),
            view.findViewById(R.id.button_p),
            view.findViewById(R.id.button_q),
            view.findViewById(R.id.button_r),
            view.findViewById(R.id.button_s),
            view.findViewById(R.id.button_t),
            view.findViewById(R.id.button_u),
            view.findViewById(R.id.button_v),
            view.findViewById(R.id.button_w),
            view.findViewById(R.id.button_x),
            view.findViewById(R.id.button_y),
            view.findViewById(R.id.button_z)
            // and so on for all the letter buttons
        )

        updateKeyboard()

        for (button in letterButtons) {

            button.setOnClickListener { selectLetter(button) }
        }
        hintButton = view.findViewById(R.id.button_hint)
        hintButton.setOnClickListener { showHint() }

        restartButton = view.findViewById(R.id.button_new_word)
        restartButton.setOnClickListener {
            listener?.restartApp()
        }

        resultLayout = view.findViewById(R.id.result)
        wonView = view.findViewById(R.id.wonText)
        lostView = view.findViewById(R.id.lostText)
        keyboard = view.findViewById(R.id.keyboard)
        choose = view.findViewById(R.id.choose)

        checkWinCondition()

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as RestartListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun selectLetter(button: Button) {
        // Disable the button to prevent selecting it again
        button.isEnabled = false
        button.setBackgroundColor(Color.LTGRAY)
        button.setTextColor(Color.GRAY)
        button.setClickable(false)

        //Store selected letter
        lettersClicked.add(button.text.toString())

        // Pass the selected letter to the main game fragment
        val parentFragment = GameFragment()


        if (parentFragment is GameFragment) {

            println("----------------got GameFragment as parentFragment")

            gameFragment.checkLetter(button.text.toString())
            gameFragment.useTurn()
            checkWinCondition()
        } else {
            println("----------------did not got GameFragment as parentFragment")
        }
    }

    private fun checkWinCondition() {
        when (gameFragment.checkWinCondition()){
            1 -> {
                //Game won
                keyboard.visibility = View.GONE
                choose.visibility = View.GONE
                resultLayout.visibility = View.VISIBLE
                wonView.visibility = View.VISIBLE
            }
            -1 -> {
                //Game lost
                choose.visibility = View.GONE
                keyboard.visibility = View.GONE
                resultLayout.visibility = View.VISIBLE
                lostView.visibility = View.VISIBLE
            }
        }
    }

    private fun showHint() {
//        val gameFragment = parentFragment as GameFragment
        when (hintCount) {
            0 -> {
                // Display the hint message
                if (gameFragment.chosenWord == getString(R.string.a)) {
                    gameFragment.showHint("What do you do with a book?.")
                }

                if (gameFragment.chosenWord == getString(R.string.b)) {
                    gameFragment.showHint("This is a mobile operating system")
                }

                if (gameFragment.chosenWord == getString(R.string.c)) {
                    gameFragment.showHint("It is a kind of sibling.")
                }

                if (gameFragment.chosenWord == getString(R.string.d)) {
                    gameFragment.showHint("This is used in expensive jewelry")
                }

                if (gameFragment.chosenWord == getString(R.string.e)) {
                    gameFragment.showHint("What do you get when you come out of prison.")
                }


            }
            1 -> {
                // Disable half of the remaining letter buttons
                val remainingLetters = gameFragment.getRemainingLetters()
                println("---------------showHint-remainingLetters: " + remainingLetters)

                // get length of enabled buttons and put it in buttonsEnabled list
                var disableCount = 0
                var buttonsEnabled: MutableList<Button> = mutableListOf()
                for (button in letterButtons) {
                    if (button.isEnabled == true) {
                        disableCount += 1
                        buttonsEnabled.add(button)
                    }
                }

                disableCount /= 2

                println("-------------show hint func disableCount: " + disableCount)

                var disabledCount = 0
                val size = buttonsEnabled.size

                println("-------------show hint func letters: " + letterButtons)
                for (i in 0 until size) {

                    //if (button.isEnabled &&  !remainingLetters.contains(button.text.toString())) {
                    var button = buttonsEnabled.random()
                    println("-------------show hint func letters: " + button.text.toString())
                    if (button.isEnabled && (button.text.toString() !in remainingLetters.toString())) {
                        println("-------------show hint func button.text.toString(): " + button.text.toString())
                        button.isEnabled = false
                        disabledCount++
                        if (disabledCount >= disableCount) break
                    }
                    //println(button.text.toString() !in remainingLetters.toString())
                }
                // Deduct a turn for using the hint
                gameFragment.useTurn()
            }
            2 -> {
                // Disable all vowel buttons and show the vowels
                var buttonsEnabled: MutableList<Button> = mutableListOf()
                for (button in letterButtons) {
                    if (button.isEnabled == true) {

                        buttonsEnabled.add(button)
                    }
                }
                val vowels = setOf("A", "E", "I", "O", "U")
                for (button in buttonsEnabled) {
                    if (vowels.contains(button.text.toString())) {
                        button.performClick()
                        gameFragment.incrementRemainingTurns()
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
        //gameFragment.usedHint()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("lettersClicked",ArrayList(lettersClicked))
        outState.putInt("hintCount",hintCount)
    }

    private fun updateKeyboard() {
        Log.d(TAG, "updateKeyboard: "+gameFragment.lettersClicked.size)
        hintCount = gameFragment.hintCount
        lettersClicked = gameFragment.lettersClicked
        for (l in gameFragment.lettersClicked){
            Log.d(TAG, "updateKeyboard: "+l)
            letterButtons[alphabets.indexOf(l)].isEnabled = false
            letterButtons[alphabets.indexOf(l)].setBackgroundColor(Color.LTGRAY)
            letterButtons[alphabets.indexOf(l)].setTextColor(Color.GRAY)
            letterButtons[alphabets.indexOf(l)].setClickable(false)
        }
    }
}