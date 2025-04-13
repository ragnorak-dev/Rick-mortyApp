package com.ragnorak.rick_morty.character_list.data.datasource

import com.ragnorak.network.api.RickAndMortyApi
import com.ragnorak.network.api.errorhandler.httpExceptionHandler
import com.ragnorak.network.api.response.CharacterListDto
import javax.inject.Inject

class CharacterListRemoteDataSource @Inject constructor(
    private val networkClient: com.ragnorak.network.api.RickAndMortyApi
) {

    suspend fun getCharacters(page: Int): Result<com.ragnorak.network.api.response.CharacterListDto> =
        try {
            Result.success(networkClient.getCharacters(page))
        } catch (exception: Exception) {
            Result.failure(exception.httpExceptionHandler())
        }

    suspend fun getCharactersByName(name: String): Result<com.ragnorak.network.api.response.CharacterListDto> =
        try {
            Result.success(networkClient.getCharactersByName(name))
        } catch (exception: Exception) {
            Result.failure(exception.httpExceptionHandler())
        }
}