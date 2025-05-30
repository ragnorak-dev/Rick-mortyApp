package com.ragnorak.rick_morty.character_list.data.datasource

import com.ragnorak.network.api.RickAndMortyApi
import com.ragnorak.network.api.errorhandler.OwnHttpException
import com.ragnorak.network.api.response.CharacterListDto
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

class CharacterListRemoteDataSourceTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var api: com.ragnorak.network.api.RickAndMortyApi
    private lateinit var sut: CharacterListRemoteDataSource

    @Before
    fun setup() {
        api = mockk()
        sut = CharacterListRemoteDataSource(api)
    }

    @Test
    fun `getCharacters returns success when API call is successful`() = runTest {
        val expectedResponse = mockk<com.ragnorak.network.api.response.CharacterListDto>()
        coEvery { api.getCharacters() } returns expectedResponse

        val result = sut.getCharacters(page = 0)

        assertTrue(result.isSuccess)
        assertEquals(expectedResponse, result.getOrNull())
    }

    @Test
    fun `getCharacters returns failure when API call throws exception`() = runTest {
        val exception = mockk<Exception>()
        coEvery { exception.message } returns "Not http exception"
        coEvery { api.getCharacters() } throws exception

        val result = sut.getCharacters(page = 0)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is com.ragnorak.network.api.errorhandler.OwnHttpException.Unknown)
    }
}
