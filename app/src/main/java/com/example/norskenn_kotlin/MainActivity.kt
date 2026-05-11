package com.example.norskenn_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.norskenn_kotlin.ui.theme.Norskenn_kotlinTheme

class MainActivity : ComponentActivity() {


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true
                || permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            viewModel.fetchWeatherForCurrentLocation(this)
        } else {
            viewModel.fetchWeather(65.97, 21.07)
        }
    }

    private lateinit var viewModel: WeatherViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()



        viewModel = WeatherViewModel()

        requestPermissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        setContent {


            Norskenn_kotlinTheme {
                val weather = viewModel.weather.collectAsState().value
                val loading = viewModel.loading.collectAsState().value
                val error = viewModel.error.collectAsState().value
                val currentWeather = weather

                val kpIndex = viewModel.kpIndex.collectAsState().value
                val sunriseSunset = viewModel.sunriseSunset.collectAsState().value
                val locationName = viewModel.locationName.collectAsState().value



                PullToRefreshBox(
                    isRefreshing = loading,
                    onRefresh = {
                        android.util.Log.d("PullToRefresh", "refreshing")
                        viewModel.fetchWeatherForCurrentLocation(this)
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF1a2a4a),
                                        Color(0xFF1e2e52),
                                        Color(0xFF22335a),
                                        Color(0xFF263860),
                                        Color(0xFF2d4070),
                                        Color(0xFF334878),
                                        Color(0xFF3a5282),
                                        Color(0xFF425d8e),
                                        Color(0xFF4a6898),
                                        Color(0xFF5272a0),
                                        Color(0xFF5a7caa),
                                        Color(0xFF6286b4),
                                        Color(0xFF6a8fba),
                                        Color(0xFF7098c0),
                                        Color(0xFF7aa4c8),
                                        Color(0xFF82aecf),
                                        Color(0xFF8ab5d4),
                                    )
                                )
                            )
                    ) {
                        if (loading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                LazyColumn(
                                    modifier = Modifier
                                        .padding(bottom = 1.dp)
                                        .fillMaxHeight(0.75f)
                                ) {
                                    items(8) {
                                        ForecastCardSkeleton()
                                    }
                                }
                            }
                        } else if (error != null) {
                            Text("Error: $error", color = Color.White)
                        } else if (currentWeather != null) {
                            val weatherSymbol =
                                currentWeather.properties.timeseries[0].data.next_1_hours.summary.symbol_code
                            when {

                                weatherSymbol.contains("heavyrain") ||
                                        weatherSymbol.contains("heavyrainshowers") -> HeavyRainEffect()

                                weatherSymbol.contains("lightrain") ||
                                        weatherSymbol.contains("rainshowers") ||
                                        weatherSymbol.contains("rain") -> LightRainEffect()

                                weatherSymbol.contains("heavysnow") ||
                                        weatherSymbol.contains("heavysnowshowers") -> HeavySnowEffect()

                                weatherSymbol.contains("lightsnow") ||
                                        weatherSymbol.contains("snowshowers") ||
                                        weatherSymbol.contains("snow") -> LightSnowEffect()

                                else -> {}
                            }
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = locationName ?: "Location unknown",
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(top = 70.dp)
                                        .alpha(0.9f),
                                    fontSize = 35.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                                Text(
                                    text = "Kp Index: $kpIndex",
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(top = 10.dp)
                                        .alpha(0.8f),
                                    fontSize = 27.sp,
                                )
                                Text(
                                    text = "Sunrise: ${formatTime(sunriseSunset?.properties?.sunrise?.time ?: "")}",
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .alpha(0.8f),
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "Sunset: ${formatTime(sunriseSunset?.properties?.sunset?.time ?: "")}",
                                    color = Color.White,
                                    modifier = Modifier
                                        .alpha(0.8f),
                                    fontSize = 18.sp
                                )
                            }
                            val timeseries = currentWeather.properties.timeseries.take(48)
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                LazyColumn(
                                    modifier = Modifier
                                        .padding(bottom = 1.dp)
                                        .fillMaxHeight(0.75f)
                                ) {

                                    items(timeseries) { item ->
                                        ForecastCard(
                                            item = ForecastItem(
                                                time = item.time,
                                                temperature = item.data.instant.details.air_temperature,
                                                precipitation = item.data.next_1_hours.details.precipitation_amount,
                                                symbolCode = item.data.next_1_hours.summary.symbol_code
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }


            }
        }
    }
}