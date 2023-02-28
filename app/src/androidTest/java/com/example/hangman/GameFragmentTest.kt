package com.example.hangman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import kotlin.collections.mutableSetOf


@RunWith(AndroidJUnit4::class)
class GameFragmentTest {

    private lateinit var scenario: FragmentScenario<GameFragment>

    @Before
    fun setUp() {
        scenario = FragmentScenario.launchInContainer(GameFragment::class.java)
    }

    @Test
    fun checkLetter_GuessedLetterExists() {
        scenario.onFragment { fragment ->
            fragment.checkLetter("a")
            fragment.checkLetter("e")
            assertEquals(setOf("a", "e"), fragment.guessedLetters)
        }
    }

    @Test
    fun checkLetter_GuessedLetterDoesNotExist() {
        scenario.onFragment { fragment ->
            fragment.checkLetter("x")
            assertEquals(setOf("x"), fragment.guessedLetters)
        }
    }

    @Test
    fun checkWinCondition_GameWon() {
        scenario.onFragment { fragment ->
            fragment.chosenWord = "test"
            fragment.checkLetter("t")
            fragment.checkLetter("e")
            fragment.checkLetter("s")
            assertEquals(1, fragment.checkWinCondition())
        }
    }

    @Test
    fun checkWinCondition_GameLost() {
        scenario.onFragment { fragment ->
            fragment.chosenWord = "test"
            fragment.remainingTurns = 0
            assertEquals(-1, fragment.checkWinCondition())
        }
    }

    @Test
    fun testLoseGame() {
        val fragment = GameFragment()
        fragment.chosenWord = "hangman"
        fragment.remainingTurns = 0
        val result = fragment.checkWinCondition()
        assertEquals(result, -1)
    }

    @Test
    fun checkWinCondition_returnsOneIfGameIsWon() {
        // Create a new GameFragment
        val fragment = GameFragment()

        // Set the chosenWord to "test" and the guessedLetters to ["t", "e", "s"]
        fragment.chosenWord = "test"
        fragment.guessedLetters = mutableSetOf("t", "e", "s")

        // Call the checkWinCondition function
        val result = fragment.checkWinCondition()

        // Check that the result is 1
        assertEquals(1, result)
    }




}
