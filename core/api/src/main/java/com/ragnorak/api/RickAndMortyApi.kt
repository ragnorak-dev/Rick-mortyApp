package com.ragnorak.api

import com.ragnorak.api.response.CharacterDto
import com.ragnorak.api.response.CharacterListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(): CharacterListDto

    @GET("character/{id}")
    suspend fun getCharacterDetails(@Path("id") characterId: Int): CharacterDto

}