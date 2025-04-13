package com.ragnorak.network.api

import com.ragnorak.network.api.response.CharacterDto
import com.ragnorak.network.api.response.CharacterListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int = 0): CharacterListDto

    @GET("character/{id}")
    suspend fun getCharacterDetails(@Path("id") characterId: Int): CharacterDto

    @GET("character")
    suspend fun getCharactersByName(@Query("name") name: String): CharacterListDto

}