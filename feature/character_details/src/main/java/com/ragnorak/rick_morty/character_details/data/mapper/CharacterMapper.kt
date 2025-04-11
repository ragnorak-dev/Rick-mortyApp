package com.ragnorak.rick_morty.character_details.data.mapper

import com.ragnorak.api.response.CharacterDto
import com.ragnorak.persistence.entity.CharacterEntity
import com.ragnorak.rick_morty.character_details.domain.model.CharacterDetailsModel

fun CharacterDto.toModel(): CharacterDetailsModel =
    CharacterDetailsModel(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        image = image
    )

fun CharacterEntity.toModel(): CharacterDetailsModel =
    CharacterDetailsModel(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        image = image
    )

fun CharacterDto.toEntity(): CharacterEntity =
    CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        image = image
    )