package com.ragnorak.network.di

import com.ragnorak.network.NetworkClient
import com.ragnorak.network.retrofit.NetworkClientRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkClient(): NetworkClient = NetworkClientRetrofit()

}