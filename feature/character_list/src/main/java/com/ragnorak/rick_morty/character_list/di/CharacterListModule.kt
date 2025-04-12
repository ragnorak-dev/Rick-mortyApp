package com.ragnorak.rick_morty.character_list.di

import com.ragnorak.api.RickAndMortyApi
import com.ragnorak.persistence.dao.CharacterDao
import com.ragnorak.rick_morty.character_list.data.datasource.CharacterListLocalDataSource
import com.ragnorak.rick_morty.character_list.data.datasource.CharacterListRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CharacterListModule {

    @Provides
    fun provideCharacterLisRemoteDataSource(rickAndMortyApi: RickAndMortyApi): CharacterListRemoteDataSource =
        CharacterListRemoteDataSource(rickAndMortyApi)

    @Provides
    fun provideCharacterLisLocalDataSource(characterDao: CharacterDao): CharacterListLocalDataSource =
        CharacterListLocalDataSource(characterDao)
}