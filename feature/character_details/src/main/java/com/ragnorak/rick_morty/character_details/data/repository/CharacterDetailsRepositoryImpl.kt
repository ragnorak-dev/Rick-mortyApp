package com.ragnorak.rick_morty.character_details.data.repository

import com.ragnorak.rick_morty.character_details.data.datasource.CharacterDetailsRemoteDataSource
import com.ragnorak.rick_morty.character_details.data.mapper.toModel
import com.ragnorak.rick_morty.character_details.domain.model.CharacterModel
import com.ragnorak.rick_morty.character_details.domain.repository.CharacterDetailsRepository
import javax.inject.Inject

class CharacterDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterDetailsRemoteDataSource
) : CharacterDetailsRepository {

    override suspend fun getCharacterDetails(characterId: Int): Result<CharacterModel> =
        remoteDataSource.getCharacterDetails(characterId).map { it.toModel() }
}