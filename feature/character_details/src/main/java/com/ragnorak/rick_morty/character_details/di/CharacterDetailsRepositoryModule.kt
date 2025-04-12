package com.ragnorak.rick_morty.character_details.di

import com.ragnorak.rick_morty.character_details.data.repository.CharacterDetailsRepositoryImpl
import com.ragnorak.rick_morty.character_details.domain.repository.CharacterDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CharacterDetailsRepositoryModule {

    @Binds
    abstract fun bindCharacterDetailsRepository(
        impl: CharacterDetailsRepositoryImpl
    ): CharacterDetailsRepository

}