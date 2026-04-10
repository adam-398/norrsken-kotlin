package com.example.norskenn_kotlin

import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

/**
 * 2nd step API Service
 * Defines the contract for the yr.no weather endpoint.
 * Retrofit generates the implementation at runtime
 * @Query param append lat/lon to the URL for specific location data
 */
interface WeatherAPIService  {
    @GET ("weatherapi/locationforecast/2.0/compact")
    suspend fun  getWeather (
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): WeatherResponse
}

/**
* 2nd step API Service
 * Defines the contract for the NOAA Aurora endpoint.
 * Retrofit generates the implementation at runtime
 * No param as the data is not location specific
 */
interface AuroraAPIService {
    @GET ("json/planetary_k_index_1m.json")
    suspend fun getKpIndex(): List<KpIndex>
}

/**
 * 2nd step API service
 * Defines the contract for the yr.no sunrise/sunset endpoint.
 * Retrofit generates the implementation at runtime
 * @Query param append lat/lon to the URL for specific location data
 */
interface SunriseSunsetAPIService {
    @GET ("weatherapi/sunrise/3.0/sun")
    suspend fun getSunriseSunset(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("date") date: String,
        @Query("offset") offset: String
    ): SunriseSunsetResponse
}