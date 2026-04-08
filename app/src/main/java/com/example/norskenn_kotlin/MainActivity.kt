package com.example.norskenn_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF05080f),
                                    Color(0xFF070c1a),
                                    Color(0xFF0a0f2a),
                                    Color(0xFF0c1540),
                                    Color(0xFF0d1a4a),
                                    Color(0xFF122260),
                                    Color(0xFF1a2d6e),
                                    Color(0xFF223880),
                                    Color(0xFF2a4d8a),
                                    Color(0xFF335a98),
                                    Color(0xFF3d6aaa),
                                    Color(0xFF4d7ab8),
                                    Color(0xFF5a8abf),
                                    Color(0xFF6a97c8),
                                    Color(0xFF7aaad4),
                                    Color(0xFF8abade),
                                    Color(0xFFa0c4e8),
                                    Color(0xFFb8d4f0),
                                    Color(0xFFc8dff0),
                                    Color(0xFFd8e8f0),
                                    Color(0xFFe8c99a),
                                    Color(0xFFf0a060),
                                )
                            )
                        )
                ) {
                    if (loading) {
                        Text("Loading...", color = Color.White)
                    } else if (error != null) {
                        Text("Error: $error", color = Color.White)
                    } else if (currentWeather != null) {
                        Text (
                            text = "Kp Index: $kpIndex",
                            color = Color.White,
                        modifier = Modifier
                            .padding(top = 100.dp)
                            .align(Alignment.TopCenter),
                            fontSize = 25.sp
                        )
                        val timeseries = currentWeather.properties.timeseries.take(12)
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            LazyRow(
                                modifier = Modifier
                                    .padding(bottom = 1.dp)
                                    .height(300.dp)
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