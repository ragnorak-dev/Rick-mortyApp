package com.ragnorak.rick_morty.character_list.ui

import app.cash.turbine.test
import com.ragnorak.rick_morty.character_list.CoroutinesTestRule
import com.ragnorak.rick_morty.character_list.domain.model.CharacterListModel
import com.ragnorak.rick_morty.character_list.domain.model.CharacterModel
import com.ragnorak.rick_morty.character_list.domain.model.PaginationInfoModel
import com.ragnorak.rick_morty.character_list.domain.repository.CharacterListRepository
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
class CharacterListViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var repository: CharacterListRepository
    private lateinit var sut: CharacterListViewModel

    @Before
    fun setUp() {
        repository = mockk()
        sut = CharacterListViewModel(repository)
    }

    @Test
    fun `onIntent LoadCharacters emits Success when repository succeeds`() = runTest {
        val characters = listOf(CharacterModel(1, "Rick", "", "", "", "", ""))
        val paginated = CharacterListModel(
            characterList = characters,
            pagInfo = PaginationInfoModel(currentPage = 1, next = 2, prev = null)
        )
        coEvery { repository.getCharacters(any()) } returns Result.success(paginated)

        sut.characterListState.test {
            sut.onIntent(CharacterListIntent.LoadCharacters)
            advanceUntilIdle()

            assertTrue(awaitItem() is ViewState.Idle)
            assertTrue(awaitItem() is ViewState.Loading)
            val success = awaitItem()
            assertTrue(success is ViewState.Success<List<CharacterModel>>)
            assertEquals(characters, (success as ViewState.Success<List<CharacterModel>>).data)
        }
    }

    @Test
    fun `onIntent LoadCharacters emits Error when repository fails`() = runTest {
        val errorMsg = "Something went wrong"
        coEvery { repository.getCharacters(any()) } returns Result.failure(Exception(errorMsg))

        sut.characterListState.test {
            sut.onIntent(CharacterListIntent.LoadCharacters)
            advanceUntilIdle()

            assertTrue(awaitItem() is ViewState.Idle)
            assertTrue(awaitItem() is ViewState.Loading)
            val success = awaitItem()
            assertTrue(success is ViewState.Error)
        }
    }
}