package com.ragnorak.rick_morty.character_list.data.datasource

import com.ragnorak.api.RickAndMortyApi
import com.ragnorak.api.errorhandler.OwnHttpException
import com.ragnorak.api.response.CharacterResponse
import com.ragnorak.rick_morty.character_list.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterListDataSourceTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var api: RickAndMortyApi
    private lateinit var dataSource: CharacterListDataSource

    @Before
    fun setup() {
        api = mockk()
        dataSource = CharacterListDataSource(api)
    }

    @Test
    fun `getCharacters returns success when API call is successful`() = runTest {
        val expectedResponse = mockk<CharacterResponse>()
        coEvery { api.getCharacters() } returns expectedResponse

        val result = dataSource.getCharacters()

        assertTrue(result.isSuccess)
        assertEquals(expectedResponse, result.getOrNull())
    }

    @Test
    fun `getCharacters returns failure when API call throws exception`() = runTest {
        val exception = mockk<Exception>()
        coEvery { exception.message } returns "Not http exception"
        coEvery { api.getCharacters() } throws exception

        val result = dataSource.getCharacters()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is OwnHttpException.Unknown)
    }
}
