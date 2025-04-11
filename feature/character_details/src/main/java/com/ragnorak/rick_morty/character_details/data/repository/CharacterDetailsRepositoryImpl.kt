package com.ragnorak.rick_morty.character_details.data.repository

import com.ragnorak.persistence.validatecache.CacheValidator
import com.ragnorak.rick_morty.character_details.data.datasource.CharacterDetailsLocalDataSource
import com.ragnorak.rick_morty.character_details.data.datasource.CharacterDetailsRemoteDataSource
import com.ragnorak.rick_morty.character_details.data.mapper.toEntity
import com.ragnorak.rick_morty.character_details.data.mapper.toModel
import com.ragnorak.rick_morty.character_details.domain.model.CharacterDetailsModel
import com.ragnorak.rick_morty.character_details.domain.repository.CharacterDetailsRepository
import javax.inject.Inject

class CharacterDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterDetailsRemoteDataSource,
    private val localDataSource: CharacterDetailsLocalDataSource
) : CharacterDetailsRepository {

    override suspend fun getCharacterDetails(characterId: Int): Result<CharacterDetailsModel> {
        val cached = localDataSource.getCharacterDetails(characterId)
       return if (CacheValidator.isCacheValid(cached.getOrNull()?.lastUpdated) && cached.isSuccess) {
            cached.map { it.toModel() }
        } else {
            remoteDataSource.getCharacterDetails(characterId).map { dto ->
                localDataSource.saveCharacterDetails(dto.toEntity())
                dto.toModel()
            }
        }
    }
}