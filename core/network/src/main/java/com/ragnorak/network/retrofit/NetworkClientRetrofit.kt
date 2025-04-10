package com.ragnorak.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ragnorak.network.Constants.BASE_URL
import com.ragnorak.network.NetworkClient
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class NetworkClientRetrofit : NetworkClient {

    private val contentType = "application/json".toMediaType()

    override fun buildNetworkClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
}