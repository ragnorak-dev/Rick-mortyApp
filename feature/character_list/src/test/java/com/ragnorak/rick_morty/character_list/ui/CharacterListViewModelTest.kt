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
import kotlinx.coroutines.delay
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
        val characters = listOf(CharacterModel(
            1, "Rick", "", "", "", "", ""))
        val paginated = CharacterListModel(
            characterList = characters,
            pagInfo = PaginationInfoModel(currentPage = 1, next = 2, prev = null)
        )
        coEvery { repository.getCharacters(any()) } coAnswers {
            delay(1)
            Result.success(paginated)
        }

        sut.characterListState.test {
            sut.onIntent(CharacterListIntent.LoadCharacters)
            advanceUntilIdle()

            assertTrue(awaitItem().listState is ViewState.Idle)
            assertTrue(awaitItem().listState is ViewState.Loading)
            val success = awaitItem()
            assertTrue(success.listState is ViewState.Success<List<CharacterModel>>)
            assertEquals(
                characters,
                (success.listState as ViewState.Success<List<CharacterModel>>).data
            )
        }
    }

    @Test
    fun `onIntent LoadCharacters emits Error when repository fails`() = runTest {
        val errorMsg = "Something went wrong"
        coEvery { repository.getCharacters(any()) } coAnswers {
            delay(1)
            Result.failure(Exception(errorMsg))
        }

        sut.characterListState.test {
            sut.onIntent(CharacterListIntent.LoadCharacters)
            advanceUntilIdle()

            assertTrue(awaitItem().listState is ViewState.Idle)
            assertTrue(awaitItem().listState is ViewState.Loading)
            assertTrue(awaitItem().listState is ViewState.Error)
        }
    }

    @Test
    fun `onIntent UpdateSearchQuery filters local list`() = runTest {
        val characters = listOf(
            CharacterModel(1, "Rick", "", "", "", "", ""),
            CharacterModel(2, "Morty", "", "", "", "", "")
        )
        val paginated = CharacterListModel(
            characterList = characters,
            pagInfo = PaginationInfoModel(currentPage = 1, next = 2, prev = null)
        )
        coEvery { repository.getCharacters(any()) } returns Result.success(paginated)
        sut.onIntent(CharacterListIntent.LoadCharacters)
        sut.onIntent(CharacterListIntent.UpdateSearchQuery("Rick"))
        advanceUntilIdle()

        sut.characterListState.test {
            val filtered = awaitItem()
            assertTrue(filtered.listState is ViewState.Success)
            assertEquals(listOf(characters[0]), filtered.filteredList)
        }
    }

    @Test
    fun `onIntent RefreshingCharacters reloads data`() = runTest {
        val characters = listOf(CharacterModel(1, "Summer", "", "", "", "", ""))
        val paginated = CharacterListModel(
            characterList = characters,
            pagInfo = PaginationInfoModel(currentPage = 1, next = null, prev = null)
        )
        coEvery { repository.getCharacters(any()) } returns Result.success(paginated)
        sut.onIntent(CharacterListIntent.RefreshingCharacters)
        advanceUntilIdle()

        sut.characterListState.test {
            val refreshed = awaitItem()
            assertTrue(refreshed.listState is ViewState.Success)
            assertEquals(characters, (refreshed.listState as ViewState.Success).data)
        }
    }

    @Test
    fun `onIntent LoadMoreCharacters appends data`() = runTest {
        val charactersPage1 = listOf(
            CharacterModel(
                1, "Rick", "", "", "", "", ""
            )
        )
        val charactersPage2 = listOf(CharacterModel(
            2, "Morty", "", "", "", "", ""))
        val page1 = CharacterListModel(
            characterList = charactersPage1,
            pagInfo = PaginationInfoModel(0, next = 1, prev = null)
        )
        val page2 = CharacterListModel(
            characterList = charactersPage2,
            pagInfo = PaginationInfoModel(1, next = null, prev = 0)
        )

        coEvery { repository.getCharacters(0) } returns Result.success(page1)
        coEvery { repository.getCharacters(1) } returns Result.success(page2)
        sut.onIntent(CharacterListIntent.LoadCharacters)
        sut.onIntent(CharacterListIntent.LoadMoreCharacters)
        advanceUntilIdle()

        sut.characterListState.test {
            val paginated = awaitItem()
            assertTrue(paginated.listState is ViewState.Success)
            assertEquals(2, (paginated.listState as ViewState.Success).data.size)
        }
    }
}