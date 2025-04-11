package com.ragnorak.rick_morty.character_details.domain.repository

import com.ragnorak.rick_morty.character_details.domain.model.CharacterDetailsModel

interface CharacterDetailsRepository {

    suspend fun getCharacterDetails(characterId: Int): Result<CharacterDetailsModel>
}