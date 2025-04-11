package com.ragnorak.persistence.di

import android.content.Context
import androidx.room.Room
import com.ragnorak.persistence.dao.CharacterDao
import com.ragnorak.persistence.ddbb.CacheDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideCharacterListDao(@ApplicationContext appContext: Context): CharacterDao =
        Room.databaseBuilder(
            appContext,
            CacheDataBase::class.java,
            "cache_database",
        ).build().characterDao()
}