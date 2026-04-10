package com.example.norskenn_kotlin

/**
 * Step 1, Data Classes
 * Describe the shape of each API JSON response.
 * Gson deserialises the JSON into these classes automatically.
 * Field names must match the JSON keys exactly.
 */


/**
 * yr.no weather API, nested JSON response.
 * Each class represents one level of nesting in the JSON.
 * Top level wrapper for the yr.no weather API response.
 */
data class WeatherResponse(
    val properties: Properties
)

/** Contains the list of hourly forecast entries. */
data class Properties(
    val timeseries: List<TimeSeries>
)

/** One hourly forecast entry, holds the time and all data for that hour*/
data class TimeSeries(
    val time: String,
    val data: TimeSeriesData
)

/** Splits the hourly data into instant readings and next hour forecast. */
data class TimeSeriesData(
    val instant: Instant,
    val next_1_hours: Next1Hours
)

/** Current instant weather readings. */
data class Instant(
    val details: Details
)

/** The specific values for the current instant. */
data class Details(
    val air_temperature: Double
)

/** Forecast summary and details for the next hour. */
data class Next1Hours(
    val summary: Summary,
    val details: PrecipitationDetails
)

/** Weather condition icon code for the next hour. */
data class Summary(
    val symbol_code: String
)

/** Precipitation forecast for the next hour. */
data class PrecipitationDetails(
    val precipitation_amount: Double
)

/**
 * NOAA K-Index API, flat JSON response.
 * The API returns a list of these directly, List<KpIndex>.
 * No wrapper class needed as there is no nesting.
 * Take the last item in the list for the most recent reading.
 */
data class KpIndex (
    val kp_index: Int
)

/**
 * yr.no sunrise/sunset API, nested JSON response.
 * Each class represents one level of nesting in the JSON.
 * Top level wrapper for the yr.no weather API response.
 */
data class SunriseSunsetResponse (
    val properties: SunriseSunsetProperties
)


data class SunriseSunsetProperties (
    val sunrise: SunriseTime,
    val sunset: SunsetTime
)

data class SunriseTime (
    val time: String
)

data class SunsetTime (
    val time: String
)