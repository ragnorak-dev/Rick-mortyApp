package com.ragnorak.rick_morty.character_list.di

import com.ragnorak.api.RickAndMortyApi
import com.ragnorak.rick_morty.character_list.data.datasource.CharacterListDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterListModule {

    @Provides
    fun provideCharacterListUseCase(rickAndMortyApi: RickAndMortyApi): CharacterListDataSource {
        return CharacterListDataSource(rickAndMortyApi)
    }
}