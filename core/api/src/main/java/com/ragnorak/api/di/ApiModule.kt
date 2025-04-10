package com.ragnorak.api.di

import com.ragnorak.api.RickAndMortyApi
import com.ragnorak.network.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideRickAndMortyApi(networkClient: NetworkClient): RickAndMortyApi {
        return networkClient.buildNetworkClient().create(RickAndMortyApi::class.java)
    }

}