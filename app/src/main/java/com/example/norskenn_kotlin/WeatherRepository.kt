package com.example.norskenn_kotlin

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "NorsKenn/1.0 github.com/yourusername/norskenn")
                .build()
            chain.proceed(request)
        }
        .build()

    private val api = Retrofit.Builder()
        .baseUrl("https://api.met.no/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherAPIService::class.java)

    suspend fun getWeather(lat: Double, lon: Double): WeatherResponse {
        return api.getWeather(lat, lon)
    }
}