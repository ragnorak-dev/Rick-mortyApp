package com.ragnorak.rick_morty.character_details.data.mapper

import com.ragnorak.api.response.CharacterDto
import com.ragnorak.rick_morty.character_details.domain.model.CharacterModel

fun CharacterDto.toModel(): CharacterModel =
    CharacterModel(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        image = image
    )