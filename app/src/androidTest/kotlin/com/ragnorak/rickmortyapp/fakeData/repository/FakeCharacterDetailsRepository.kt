package com.ragnorak.rickmortyapp.fakeData.repository

import com.ragnorak.rick_morty.character_details.data.mapper.toModel
import com.ragnorak.rick_morty.character_details.domain.model.CharacterDetailsModel
import com.ragnorak.rick_morty.character_details.domain.repository.CharacterDetailsRepository
import com.ragnorak.rickmortyapp.fakeData.dto.fakeCharacterListDto

class FakeCharacterDetailsRepository : CharacterDetailsRepository {

    override suspend fun getCharacterDetails(characterId: Int): Result<CharacterDetailsModel> {
        val fakeCharacterDetailsDto = fakeCharacterListDto.results.find { it.id == characterId }
        return fakeCharacterDetailsDto?.let {
            Result.success(it.toModel())
        } ?: Result.failure(Exception("Character not found"))
    }
}