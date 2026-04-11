package com.example.norskenn_kotlin

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 2nd step API Service
 * Defines the contract for the nominatim endpoint.
 * Retrofit generates the implementation at runtime
 * @Query param append lat/lon to the URL for specific location data
 */

interface LocationApiService {
    @GET("reverse")
    suspend fun getLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("format") format: String = "json"
    ): LocationResponse
}