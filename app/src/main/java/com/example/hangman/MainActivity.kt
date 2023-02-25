package com.example.hangman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), ChooseLetterFragment.RestartListener {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val gameFragment = GameFragment()
            fragmentTransaction.add(R.id.fragment_game, gameFragment)

            val chooseLetterFragment = ChooseLetterFragment(gameFragment)
            fragmentTransaction.add(R.id.fragment_choose_letter, chooseLetterFragment)
//            val hintFragment = HintFragment(gameFragment)
//            fragmentTransaction.add(R.id.fragment_hint, hintFragment)
            fragmentTransaction.commit()

        }

        override fun restartApp() {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
