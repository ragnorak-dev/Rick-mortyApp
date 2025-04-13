package com.ragnorak.rick_morty.character_details.data.datasource

import com.ragnorak.network.api.RickAndMortyApi
import com.ragnorak.network.api.errorhandler.OwnHttpException
import com.ragnorak.network.api.response.CharacterDto
import com.ragnorak.rick_morty.character_details.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterDetailsRemoteDataSourceTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var api: RickAndMortyApi
    private lateinit var sut: CharacterDetailsRemoteDataSource

    @Before
    fun setup() {
        api = mockk()
        sut = CharacterDetailsRemoteDataSource(api)
    }

    @Test
    fun `getCharacterDetails returns success when API call is successful`() = runTest {
        val characterId = 1
        val dto = mockk<CharacterDto>()
        coEvery { api.getCharacterDetails(characterId) } returns dto

        val result = sut.getCharacterDetails(characterId)

        assertTrue(result.isSuccess)
        assertEquals(dto, result.getOrNull())
    }

    @Test
    fun `getCharacterDetails returns failure when API call throws HttpException`() = runTest {
        val characterId = 1
        val exception = mockk<Exception>()
        coEvery { exception.message } returns "Not http exception"
        coEvery { api.getCharacters() } throws exception

        val result = sut.getCharacterDetails(characterId)

        TestCase.assertTrue(result.isFailure)
        TestCase.assertTrue(result.exceptionOrNull() is OwnHttpException.Unknown)
    }
}