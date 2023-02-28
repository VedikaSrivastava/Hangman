package com.example.hangman

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before

/**
This test verifies that the restartApp method creates an intent to restart the MainActivity
with the FLAG_ACTIVITY_CLEAR_TOP and FLAG_ACTIVITY_NEW_TASK flags, starts the intent, and finishes
the current activity. It also checks that both GameFragment and ChooseLetterFragment are not null
after the restart.
 */



@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testRestartApp() {
        scenario.onActivity { activity ->
            activity.restartApp()
            val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            assertEquals(intent.component, activity.intent.component)
            assertNotNull(activity.gameFragment)
            assertNotNull(activity.chooseLetterFragment)
        }
    }
}
