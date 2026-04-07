package com.example.norskenn_kotlin

/**
 * 1st step, WeatherResponse data class representing the response from the weather API. Gson will deserialize this.
 */
data class WeatherResponse(
    val properties: Properties
)

data class Properties(
    val timeseries: List<TimeSeries>
)

data class TimeSeries(
    val time: String,
    val data: TimeSeriesData
)

data class TimeSeriesData(
    val instant: Instant,
    val next_1_hours: Next1Hours
)

data class Instant(
    val details: Details
)

data class Details(
    val air_temperature: Double
)

data class Next1Hours(
    val summary: Summary,
    val details: PrecipitationDetails
)

data class Summary(
    val symbol_code: String
)

data class PrecipitationDetails(
    val precipitation_amount: Double
)