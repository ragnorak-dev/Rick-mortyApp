package com.ragnorak.rick_morty.character_list.data.repository

import com.ragnorak.api.response.CharacterListDto
import com.ragnorak.rick_morty.character_list.CoroutinesTestRule
import com.ragnorak.rick_morty.character_list.data.datasource.CharacterListLocalDataSource
import com.ragnorak.rick_morty.character_list.data.datasource.CharacterListRemoteDataSource
import com.ragnorak.rick_morty.character_list.data.mapper.toModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterListRepositoryImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var remoteDataSource: CharacterListRemoteDataSource
    private lateinit var localDataSource: CharacterListLocalDataSource
    private lateinit var sut: CharacterListRepositoryImpl

    @Before
    fun setup() {
        remoteDataSource = mockk()
        localDataSource = mockk()
        sut = CharacterListRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getCharacters returns success when dataSource returns success`() = runTest {
        val response = mockk<CharacterListDto>(relaxed = true)
        val expected = response.toModel()
        coEvery { remoteDataSource.getCharacters() } returns Result.success(response)

        val result = sut.getCharacters()

        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `getCharacters returns failure when dataSource returns failure`() = runTest {
        val exception = RuntimeException("Error")
        coEvery { remoteDataSource.getCharacters() } returns Result.failure(exception)

        val result = sut.getCharacters()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}