package com.ragnorak.rickmortyapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ragnorak.rick_morty.character_details.di.CharacterDetailsRepositoryModule
import com.ragnorak.rick_morty.character_details.domain.repository.CharacterDetailsRepository
import com.ragnorak.rick_morty.character_list.di.CharacterListRepositoryModule
import com.ragnorak.rick_morty.character_list.domain.repository.CharacterListRepository
import com.ragnorak.rickmortyapp.fakeData.repository.FakeCharacterDetailsRepository
import com.ragnorak.rickmortyapp.fakeData.repository.FakeCharacterListRepository
import com.ragnorak.ui.ComponentIdentifier
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@UninstallModules(CharacterListRepositoryModule::class, CharacterDetailsRepositoryModule::class)
@HiltAndroidTest
class MainActivityTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    val fakeListRepo: CharacterListRepository = FakeCharacterListRepository()

    @BindValue
    val fakeDetailsRepo: CharacterDetailsRepository = FakeCharacterDetailsRepository()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun characterList_isDisplayed() {
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Rick Sanchez", substring = true).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Rick Sanchez").assertIsDisplayed()
    }

    @Test
    fun characterList_clickNavigatesToDetails() {
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Morty Smith", substring = true).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Morty Smith").performClick()

        composeTestRule.onNodeWithTag(ComponentIdentifier.DETAILS_CARD_FIELD_NAME).assertIsDisplayed()
        composeTestRule.onNodeWithTag(ComponentIdentifier.DETAILS_CARD_FIELD_STATUS).assertIsDisplayed()
        composeTestRule.onNodeWithTag(ComponentIdentifier.DETAILS_CARD_FIELD_SPECIES).assertIsDisplayed()
        composeTestRule.onNodeWithTag(ComponentIdentifier.DETAILS_CARD_FIELD_TYPE).assertIsDisplayed()
        composeTestRule.onNodeWithTag(ComponentIdentifier.DETAILS_CARD_FIELD_GENDER).assertIsDisplayed()
    }
}