package com.ragnorak.rick_morty.character_details.data.datasource

import com.ragnorak.api.RickAndMortyApi
import com.ragnorak.api.errorhandler.httpExceptionHandler
import com.ragnorak.api.response.CharacterDto
import javax.inject.Inject

class CharacterDetailsRemoteDataSource @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {
    suspend fun getCharacterDetails(characterId: Int): Result<CharacterDto> =
        try {
             Result.success(rickAndMortyApi.getCharacterDetails(characterId = characterId))
        } catch (exception: Exception) {
            Result.failure(exception.httpExceptionHandler())
        }
}