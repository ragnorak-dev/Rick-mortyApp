package com.ragnorak.rick_morty.character_details.domain.model

data class CharacterDetailsModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
)
