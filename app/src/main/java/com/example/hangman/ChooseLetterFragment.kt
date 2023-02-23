package com.example.hangman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class ChooseLetterFragment(gameFragment: GameFragment) : Fragment() {
    private lateinit var letterButtons: Array<Button>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        return view
    }

    private fun selectLetter(button: Button) {
        // Disable the button to prevent selecting it again
        button.isEnabled = false
        // Pass the selected letter to the main game fragment
        val parentFragment = parentFragment
        if (parentFragment is GameFragment) {

            println("----------------got GameFragment as parentFragment")

            parentFragment.checkLetter(button.text.toString())
            parentFragment.useTurn()
        }
        else{
            println("----------------did not got GameFragment as parentFragment")
        }
    }
}