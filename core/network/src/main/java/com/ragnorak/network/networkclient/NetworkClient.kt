package com.ragnorak.network.networkclient

import retrofit2.Retrofit

interface NetworkClient {

    fun buildNetworkClient(): Retrofit
}