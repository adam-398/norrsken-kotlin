package com.example.norskenn_kotlin

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPIService  {
    @GET ("weatherapi/locationforecast/2.0/compact")
    suspend fun  getWeather (
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): WeatherResponse
}