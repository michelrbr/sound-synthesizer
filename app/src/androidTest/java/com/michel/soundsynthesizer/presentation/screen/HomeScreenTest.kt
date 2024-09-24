package com.michel.soundsynthesizer.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.michel.soundsynthesizer.presentation.screen.HomeScreen
import br.com.michel.soundsynthesizer.presentation.theme.SoundSynthesizerTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun `Home screen layout testing`() {
        composeTestRule.setContent {
            SoundSynthesizerTheme {
                HomeScreen()
            }
        }

        composeTestRule.onNodeWithText("Sine").assertIsDisplayed()
        composeTestRule.onNodeWithText("Triangle").assertIsDisplayed()
        composeTestRule.onNodeWithText("Square").assertIsDisplayed()
        composeTestRule.onNodeWithText("Saw").assertIsDisplayed()

        composeTestRule.onNodeWithText(text = "Frequency:", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(text = "Volume:", substring = true).assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Play").performClick()
        composeTestRule.onNodeWithContentDescription("Stop").assertIsDisplayed()
    }
}
