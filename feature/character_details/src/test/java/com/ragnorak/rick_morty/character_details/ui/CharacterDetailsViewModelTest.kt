package com.ragnorak.rick_morty.character_details.ui

import app.cash.turbine.test
import com.ragnorak.rick_morty.character_details.CoroutinesTestRule
import com.ragnorak.rick_morty.character_details.domain.model.CharacterDetailsModel
import com.ragnorak.rick_morty.character_details.domain.repository.CharacterDetailsRepository
import com.ragnorak.ui.ViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var repository: CharacterDetailsRepository
    private lateinit var sut: CharacterDetailsViewModel

    @Before
    fun setUp() {
        repository = mockk()
        sut = CharacterDetailsViewModel(repository)
    }

    @Test
    fun `getCharacterDetails emits Success when repository succeeds`() = runTest {
        val character = CharacterDetailsModel(
            id = 1,
            name = "Rick",
            image = "",
            status = "",
            gender = "",
            species = "",
            type = ""
        )
        coEvery { repository.getCharacterDetails(1) } returns Result.success(character)

        sut.uiState.test {
            sut.getCharacterDetails(1)
            advanceUntilIdle()

            assertTrue(awaitItem() is ViewState.Idle)
            assertTrue(awaitItem() is ViewState.Loading)
            val success = awaitItem()
            assertTrue(success is ViewState.Success<CharacterDetailsModel>)
            assertEquals(character, (success as ViewState.Success<CharacterDetailsModel>).data)
        }
    }

    @Test
    fun `getCharacterDetails emits Error when repository fails`() = runTest {
        val errorMessage = "Something went wrong"
        coEvery { repository.getCharacterDetails(1) } returns Result.failure(Exception(errorMessage))

        sut.uiState.test {
            sut.getCharacterDetails(1)
            advanceUntilIdle()

            assertTrue(awaitItem() is ViewState.Idle)
            assertTrue(awaitItem() is ViewState.Loading)
            val success = awaitItem()
            assertTrue(success is ViewState.Error)
        }
    }
}