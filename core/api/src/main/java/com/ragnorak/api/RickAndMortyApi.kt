package com.ragnorak.api

import com.ragnorak.api.response.CharacterResponse
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(): CharacterResponse
}