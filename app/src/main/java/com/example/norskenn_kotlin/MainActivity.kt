package com.example.norskenn_kotlin

import android.R.attr.fontWeight
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable

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
                        viewModel.fetchWeatherForCurrentLocation(this) },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF2d3d6e),
                                        Color(0xFF2a3d70),
                                        Color(0xFF263878),
                                        Color(0xFF243580),
                                        Color(0xFF2a4080),
                                        Color(0xFF2d4888),
                                        Color(0xFF335a98),
                                        Color(0xFF3d6aaa),
                                        Color(0xFF4d7ab8),
                                        Color(0xFF5a8abf),
                                        Color(0xFF6a97c8),
                                        Color(0xFF7aaad4),
                                        Color(0xFF8abade),
                                        Color(0xFF9abfe6),
                                        Color(0xFFa0c4e8),
                                        Color(0xFFb8d4f0),
                                        Color(0xFFc8dff0),
                                        Color(0xFFd8e8f0),
                                        Color(0xFFe0d8c0),
                                        Color(0xFFe8c88a),
                                        Color(0xFFe8b060),
                                        Color(0xFFe09050),
                                        Color(0xFFD96746),
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
                                ) {            items(8) {
                                    ForecastCardSkeleton()
                                } }
                            }
                        } else if (error != null) {
                            Text("Error: $error", color = Color.White)
                        } else if (currentWeather != null) {
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
                                LazyColumn (
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