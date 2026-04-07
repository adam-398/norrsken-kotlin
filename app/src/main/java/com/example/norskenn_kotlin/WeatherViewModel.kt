package com.example.norskenn_kotlin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

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

    fun fetchWeatherForCurrentLocation(context: Context) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val locationHelper = LocationHelper(context)
                val (lat, lon) = locationHelper.getLocation()
                _weather.value = repository.getWeather(lat, lon)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}