package com.ragnorak.network

import retrofit2.Retrofit

interface NetworkClient {

    fun buildNetworkClient(): Retrofit
}