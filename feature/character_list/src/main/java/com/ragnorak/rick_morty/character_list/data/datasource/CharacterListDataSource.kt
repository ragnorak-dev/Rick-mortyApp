package com.ragnorak.rick_morty.character_list.data.datasource

import com.ragnorak.api.RickAndMortyApi
import com.ragnorak.api.errorhandler.httpExceptionHandler
import com.ragnorak.api.response.CharacterListDto
import javax.inject.Inject

class CharacterListDataSource @Inject constructor(
    private val networkClient: RickAndMortyApi
) {

    suspend fun getCharacters(): Result<CharacterListDto> =
        try {
            Result.success(networkClient.getCharacters())
        } catch (exception: Exception) {
            Result.failure(exception.httpExceptionHandler())
        }
}