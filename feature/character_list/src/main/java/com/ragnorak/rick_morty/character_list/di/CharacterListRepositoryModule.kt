package com.ragnorak.rick_morty.character_list.di

import com.ragnorak.rick_morty.character_list.data.repository.CharacterListRepositoryImpl
import com.ragnorak.rick_morty.character_list.domain.repository.CharacterListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CharacterListRepositoryModule {

    @Binds
    abstract fun bindCharacterListRepository(
        impl: CharacterListRepositoryImpl
    ): CharacterListRepository
}