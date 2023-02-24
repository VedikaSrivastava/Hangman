package com.example.hangman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val gameFragment = GameFragment()
        fragmentTransaction.add(R.id.fragment_game, gameFragment)

        val chooseLetterFragment = ChooseLetterFragment(gameFragment)
        fragmentTransaction.add(R.id.fragment_choose_letter, chooseLetterFragment)
        val hintFragment = HintFragment()
        fragmentTransaction.add(R.id.fragment_hint, hintFragment)
        fragmentTransaction.commit()

    }
}