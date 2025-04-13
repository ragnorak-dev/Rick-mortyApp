package com.ragnorak.network.di

import com.ragnorak.network.api.RickAndMortyApi
import com.ragnorak.network.networkclient.NetworkClient
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