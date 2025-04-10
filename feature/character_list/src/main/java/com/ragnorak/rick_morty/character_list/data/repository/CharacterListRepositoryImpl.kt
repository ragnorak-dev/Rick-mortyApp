package com.ragnorak.rick_morty.character_list.data.repository

import com.ragnorak.rick_morty.character_list.data.datasource.CharacterListDataSource
import com.ragnorak.rick_morty.character_list.data.mapper.toModel
import com.ragnorak.rick_morty.character_list.domain.model.CharacterListModel
import com.ragnorak.rick_morty.character_list.domain.repository.CharacterListRepository
import javax.inject.Inject

class CharacterListRepositoryImpl @Inject constructor(
    private val dataSource: CharacterListDataSource
) : CharacterListRepository {
    override suspend fun getCharacters(): Result<CharacterListModel> =
        dataSource.getCharacters().map {
            it.toModel()
        }
}