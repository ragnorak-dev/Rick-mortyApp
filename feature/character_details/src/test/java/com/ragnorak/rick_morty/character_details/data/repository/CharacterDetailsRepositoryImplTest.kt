package com.ragnorak.rick_morty.character_details.data.repository

import com.ragnorak.api.response.CharacterDto
import com.ragnorak.rick_morty.character_details.CoroutinesTestRule
import com.ragnorak.rick_morty.character_details.data.datasource.CharacterDetailsLocalDataSource
import com.ragnorak.rick_morty.character_details.data.datasource.CharacterDetailsRemoteDataSource
import com.ragnorak.rick_morty.character_details.data.mapper.toModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterDetailsRepositoryImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var remoteDataSource: CharacterDetailsRemoteDataSource
    private lateinit var localDataSource: CharacterDetailsLocalDataSource
    private lateinit var sut: CharacterDetailsRepositoryImpl

    @Before
    fun setup() {
        remoteDataSource = mockk()
        localDataSource = mockk()
        sut = CharacterDetailsRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getCharacterDetails returns success when remoteDataSource succeeds`() = runTest {
        val characterId = 1
        val dto = mockk<CharacterDto>(relaxed = true)
        val expected = dto.toModel()

        coEvery { remoteDataSource.getCharacterDetails(characterId) } returns Result.success(dto)

        val result = sut.getCharacterDetails(characterId)

        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `getCharacterDetails returns failure when remoteDataSource fails`() = runTest {
        val characterId = 1
        val exception = RuntimeException("Something went wrong")

        coEvery { remoteDataSource.getCharacterDetails(characterId) } returns Result.failure(
            exception
        )

        val result = sut.getCharacterDetails(characterId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}