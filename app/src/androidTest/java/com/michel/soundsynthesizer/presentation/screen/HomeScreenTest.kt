package com.michel.soundsynthesizer.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import br.com.michel.soundsynthesizer.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun `Home screen layout testing`() {

        composeTestRule.onNodeWithText("Sine").assertIsDisplayed()
        composeTestRule.onNodeWithText("Triangle").assertIsDisplayed()
        composeTestRule.onNodeWithText("Square").assertIsDisplayed()
        composeTestRule.onNodeWithText("Saw").assertIsDisplayed()

        composeTestRule.onNodeWithText(text = "Frequency:", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(text = "Volume:", substring = true).assertIsDisplayed()
    }
}
