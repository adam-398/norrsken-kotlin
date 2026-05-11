package com.example.norskenn_kotlin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.Instant


/**
 * Step 4, ViewModel
 * Manges all data state for the UI and survives screen rotations.
 * Calls the repository on a background thread using viewModelScope.launch.
 * Exposes read only StateFlows for the UI to observe.
 * The UI never touches the network directly, only ready from the state flow
 */
class WeatherViewModel : ViewModel() {
    /**
     * Location name from nominatim.
     * Null until the first successful fetch
     */

    private val _locationName = MutableStateFlow<String?>(null)
    val locationName: StateFlow<String?> = _locationName
    private val locationRepository = LocationNameRepository()



    private val repository = WeatherRepository()
    /**
     * Weather data from yr.no.
     * Null until the first successful fetch
     */
    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    /**
     * Loading state, true until the first successful fetch
     */
    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    /**
     * Error state, initialized to null
     */
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    /**
     * Current K index from Noaa.
     * Null until the first successfull fetch
     */
    private val _kpIndex = MutableStateFlow<Int?>(null)

    val kpIndex: StateFlow<Int?> = _kpIndex


    /**
     * Sunrise and sunset time from yr.no.
     * Null until the first successfull fetch
     */
    private val _sunriseSunset = MutableStateFlow<SunriseSunsetResponse?>(null)

    val sunriseSunset: StateFlow<SunriseSunsetResponse?> = _sunriseSunset





    /**
     * Fetches weather for a given location.
     * Used directly when coordinates are already known.
     */
    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _weather.value = repository.getWeather(lat, lon)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    /**
     * Entry point for all data fetching on app start.
     * Gets the device location first, then fetches weather and K-Index in parallel.
     * Called from MainActivity once location permission is granted.
     */
    fun fetchWeatherForCurrentLocation(context: Context) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val locationHelper = LocationHelper(context)
                val (lat, lon) = locationHelper.getLocation()
                android.util.Log.d("Location", "lat: $lat, lon: $lon")
                val locationResponse = locationRepository.getLocationName(lat, lon)
                android.util.Log.d("LocationName", "full response: $locationResponse")
                _locationName.value = locationResponse.address.village
                    ?: locationResponse.address.town
                            ?: locationResponse.address.city
                            ?: locationResponse.address.municipality
                            ?: locationResponse.address.county
                            ?: "Unknown"
                val date = LocalDate.now().toString()
                val offset = ZoneId.systemDefault().rules.getOffset(Instant.now()).toString()
                _weather.value = repository.getWeather(lat, lon)
                fetchKpIndex()
                fetchSunriseSunset(lat, lon, date, offset)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
    /** Fetches the latest K-Index reading from NOAA.
    * Takes only the last item from the list as it is the most recent reading.
    * Called automatically by fetchWeatherForCurrentLocation on app start.
    */
    fun fetchKpIndex() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _kpIndex.value = repository.getKpIndex().last().kp_index
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    /**
     * Fetches the sunrise and sunset times for a given location and date.
     */
    fun fetchSunriseSunset(lat: Double, lon: Double, date: String, offset: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _sunriseSunset.value = repository.getSunriseSunset(lat, lon, date, offset)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
            _loading.value = false
            }
        }
    }

}