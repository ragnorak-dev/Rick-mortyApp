package com.ragnorak.rick_morty.character_details.domain.repository

import com.ragnorak.rick_morty.character_details.domain.model.CharacterModel

interface CharacterDetailsRepository {

    suspend fun getCharacterDetails(characterId: Int): Result<CharacterModel>
}