package com.ragnorak.rick_morty.character_list.data.repository

import com.ragnorak.persistence.validatecache.CacheValidator
import com.ragnorak.rick_morty.character_list.data.datasource.CharacterListRemoteDataSource
import com.ragnorak.rick_morty.character_list.data.datasource.CharacterListLocalDataSource
import com.ragnorak.rick_morty.character_list.data.mapper.toEntity
import com.ragnorak.rick_morty.character_list.data.mapper.toModel
import com.ragnorak.rick_morty.character_list.domain.model.CharacterListModel
import com.ragnorak.rick_morty.character_list.domain.repository.CharacterListRepository
import javax.inject.Inject

class CharacterListRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterListRemoteDataSource,
    private val localDataSource: CharacterListLocalDataSource
) : CharacterListRepository {

    override suspend fun getCharacters(page: Int): Result<CharacterListModel> {
        if (CacheValidator.isCacheValid()) {
           return localDataSource.getCharactersByPage(page).map { it.toModel() }
        }
        return remoteDataSource.getCharacters().map { dto ->
            localDataSource.saveCharactersPage(dto.toEntity())
            CacheValidator.updateFetchTime()
            dto.toModel()
        }
    }
}