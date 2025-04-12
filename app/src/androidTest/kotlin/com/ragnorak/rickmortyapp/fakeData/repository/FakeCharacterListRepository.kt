package com.ragnorak.rickmortyapp.fakeData.repository

import com.ragnorak.rick_morty.character_list.data.mapper.toModel
import com.ragnorak.rick_morty.character_list.domain.model.CharacterListModel
import com.ragnorak.rick_morty.character_list.domain.repository.CharacterListRepository
import com.ragnorak.rickmortyapp.fakeData.dto.fakeCharacterListByNameDto
import com.ragnorak.rickmortyapp.fakeData.dto.fakeCharacterListDto

class FakeCharacterListRepository : CharacterListRepository {

    override suspend fun getCharacters(page: Int): Result<CharacterListModel> =
        Result.success(fakeCharacterListDto.toModel())


    override suspend fun getCharactersByName(name: String): Result<CharacterListModel> =
        Result.success(fakeCharacterListByNameDto.toModel())
}