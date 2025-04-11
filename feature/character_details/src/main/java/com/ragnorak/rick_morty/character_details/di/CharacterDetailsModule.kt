package com.ragnorak.rick_morty.character_details.di

import com.ragnorak.api.RickAndMortyApi
import com.ragnorak.rick_morty.character_details.data.datasource.CharacterDetailsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CharacterDetailsModule {

    @Provides
    fun provideCharacterListUseCase(rickAndMortyApi: RickAndMortyApi): CharacterDetailsRemoteDataSource =
        CharacterDetailsRemoteDataSource(rickAndMortyApi)
}