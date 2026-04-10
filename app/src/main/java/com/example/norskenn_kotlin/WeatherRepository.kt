package com.example.norskenn_kotlin

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/**
 * Step 3, Repository
 * The only place in the app that touches the network.
 * Builds the HTTP clients and exposes the suspend functions for the ViewModel to call.
 */
class WeatherRepository {

    /**
     * Custom OkHttpClient for yr.no.
     * yr.no requires User-Agent header of app name + email.
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
     * Retrofit instance for the yr.no weather API.
     * Uses the custom OkHttp client to attach the User-Agent header.
     * GsonConverterFactory deserialises the JSON response into data classes.
     */
    private val api = Retrofit.Builder()
        .baseUrl("https://api.met.no/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherAPIService::class.java)

    /**
     * Fetches weather data for a given location from yr.no.
     * Called by the ViewModel with coordinates from the device location.
     */
    suspend fun getWeather(lat: Double, lon: Double): WeatherResponse {
        return api.getWeather(lat, lon)
    }

    /**
     * Retrofit instance for the NOAA K-Index API.
     * No custom client needed, NOAA does not require a User-Agent header.
     */
    private val auroraAPI = Retrofit.Builder()
        .baseUrl("https://services.swpc.noaa.gov/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuroraAPIService::class.java)

    /**
     * Fetches the latest K-Index readings from NOAA.
     * Returns a flat list, take the last item for the most recent reading.
     */
    suspend fun getKpIndex(): List<KpIndex> {
        return auroraAPI.getKpIndex()
    }


    private val sunriseSunsetAPI = Retrofit.Builder()
        .baseUrl("https://api.met.no/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SunriseSunsetAPIService::class.java)

    suspend fun getSunriseSunset(
        lat: Double,
        lon: Double,
        date: String,
        offset: String
    ): SunriseSunsetResponse {
        return sunriseSunsetAPI.getSunriseSunset(lat, lon, date, offset)
    }
}
