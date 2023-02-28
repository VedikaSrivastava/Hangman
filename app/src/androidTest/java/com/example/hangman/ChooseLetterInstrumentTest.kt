package com.example.hangman

import android.view.View
import android.widget.Button
import junit.framework.TestCase.*
import kotlinx.android.synthetic.main.fragment_choose_letter.*
import org.junit.Test

class ChooseLetterFragmentTest {

    @Test
    fun test_selectLetter_buttonDisabledOnClick() {
        // Arrange
        val fragment = ChooseLetterFragment()
        val button = Button(fragment.context)
        button.isEnabled = true
        button.isClickable = true

        // Act
        fragment.selectLetter(button)

        // Assert
        assertFalse(button.isEnabled)
        assertFalse(button.isClickable)
    }

    @Test
    fun test_selectLetter_letterAddedToClickedLetters() {
        // Arrange
        val fragment = ChooseLetterFragment()
        val button = Button(fragment.context)
        button.text = "A"

        // Act
        fragment.selectLetter(button)

        // Assert
        assertTrue(fragment.lettersClicked.contains(button.text))
    }

//    @Test
//    fun test_checkWinCondition_gameWon() {
//        // Arrange
//        val fragment = ChooseLetterFragment()
//        val gameFragment = GameFragment()
//        gameFragment.chosenWord = "APPLE"
//        gameFragment.currentWord = "APPLE"
//        fragment.gameFragment = gameFragment
//
//        // Act
//        fragment.checkWinCondition()
//
//        // Assert
//        assertEquals(View.VISIBLE, fragment.resultLayout.visibility)
//        assertEquals(View.VISIBLE, fragment.wonView.visibility)
//        assertEquals(View.GONE, fragment.choose.visibility)
//        assertEquals(View.GONE, fragment.keyboard.visibility)
//    }
//
//    @Test
//    fun test_checkWinCondition_gameLost() {
//        // Arrange
//        val fragment = ChooseLetterFragment()
//        val gameFragment = GameFragment()
//        gameFragment.chosenWord = "APPLE"
//        gameFragment.currentWord = "_____" // all turns used up
//        fragment.gameFragment = gameFragment
//
//        // Act
//        fragment.checkWinCondition()
//
//        // Assert
//        assertEquals(View.VISIBLE, fragment.resultLayout.visibility)
//        assertEquals(View.VISIBLE, fragment.lostView.visibility)
//        assertEquals(View.GONE, fragment.choose.visibility)
//        assertEquals(View.GONE, fragment.keyboard.visibility)
//    }
//
//    @Test
//    fun test_showHint_hintDisplayed() {
//        // Arrange
//        val fragment = ChooseLetterFragment()
//        val gameFragment = GameFragment()
//        gameFragment.chosenWord = "APPLE"
//        //gameFragment.currentWord = "_____" // all turns used up
//        fragment.gameFragment = gameFragment
//        val expectedHint = "What do you do with a book?."
//
//        // Act
//        fragment.showHint()
//
//        // Assert
//        assertEquals(expectedHint, gameFragment.hint)
//    }
}

