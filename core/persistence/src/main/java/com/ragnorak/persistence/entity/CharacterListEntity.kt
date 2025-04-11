package com.ragnorak.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "character_list")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val lastUpdated: Long = System.currentTimeMillis()
)

@Entity(tableName = "character_list_entity")
data class CharacterListEntity(
    @PrimaryKey val page: Int,
    val count: Int,
    val pages: Int,
    val next: Int?,
    val prev: Int?,
    val results: List<CharacterEntity>
)
