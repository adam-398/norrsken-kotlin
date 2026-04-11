package com.example.norskenn_kotlin

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Step 3, Repository
 * The only place in the app that touches the network.
 * Builds the HTTP clients and exposes the suspend functions for the ViewModel to call.
 */

class LocationNameRepository {

    /**
     * Custom OkHttpClient for nominatim.
     * nominatim requires User-Agent header of app name + email.
     */
    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "Norskenn/1.0 adamhodges@live.co.uk")
                .build()
            chain.proceed(request)
        }
        .build()

    /**
     * Retrofit instance for the nominatim API.
     * Uses the custom OkHttp client to attach the User-Agent header.
     * GsonConverterFactory deserialises the JSON response into data classes.
     */

    private val api = Retrofit.Builder()
        .baseUrl("https://nominatim.openstreetmap.org/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LocationApiService::class.java)

    suspend fun getLocationName(lat: Double, lon: Double): LocationResponse {
        return api.getLocation(lat, lon)
    }
}