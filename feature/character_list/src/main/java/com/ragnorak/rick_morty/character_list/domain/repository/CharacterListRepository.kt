package com.ragnorak.rick_morty.character_list.domain.repository

import com.ragnorak.rick_morty.character_list.domain.model.CharacterListModel

interface CharacterListRepository {

    suspend fun getCharacters(page: Int = 0): Result<CharacterListModel>
}