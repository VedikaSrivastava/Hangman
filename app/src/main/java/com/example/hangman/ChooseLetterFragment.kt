package com.example.hangman

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment


class ChooseLetterFragment(val gameFragment: GameFragment) : Fragment() {
    private lateinit var letterButtons: Array<Button>
    private lateinit var hintButton: Button
    private lateinit var restartButton: Button
    private var hintCount = 0
    //val gameFragment = GameFragment()

//    companion object {
//        fun newInstance(): ChooseLetterFragment {
//            return ChooseLetterFragment(gameFragment)
//        }
//    }

    private var listener: RestartListener? = null

    interface RestartListener {
        fun restartApp()
    }
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
        for (button in letterButtons) {

            button.setOnClickListener { selectLetter(button) }
        }
        hintButton = view.findViewById(R.id.button_hint)
        hintButton.setOnClickListener { showHint() }

        restartButton = view.findViewById(R.id.button_new_word)
        restartButton.setOnClickListener {
            listener?.restartApp()
        }

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
        // Pass the selected letter to the main game fragment
        val parentFragment = GameFragment()


        if (parentFragment is GameFragment) {

            println("----------------got GameFragment as parentFragment")

            gameFragment.checkLetter(button.text.toString())
            gameFragment.useTurn()
        } else {
            println("----------------did not got GameFragment as parentFragment")
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
                        gameFragment.incrementrRemainingTurns()

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