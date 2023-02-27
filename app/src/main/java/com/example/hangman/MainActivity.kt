package com.example.hangman

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity(), ChooseLetterFragment.RestartListener {

    private var gameFragment: GameFragment? = null
    private var chooseLetterFragment: ChooseLetterFragment? = null

    override fun restartApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        var fragment = fragmentManager.findFragmentById(R.id.fragment_game)
        if (fragment == null) {
            gameFragment = GameFragment()
            fragmentTransaction.add(R.id.fragment_game, gameFragment!!)
            chooseLetterFragment = ChooseLetterFragment(gameFragment!!)
            fragmentTransaction.add(R.id.fragment_choose_letter, chooseLetterFragment!!)
            fragmentTransaction.commit()
        }
        else{
            val f = fragmentManager.findFragmentById(R.id.fragment_choose_letter)
            fragmentTransaction.remove(f!! as ChooseLetterFragment)
            chooseLetterFragment = ChooseLetterFragment(fragment!! as GameFragment)
            fragmentTransaction.add(R.id.fragment_choose_letter, chooseLetterFragment!!)
            fragmentTransaction.commit()
        }
    }
}