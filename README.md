# Norrsken
A weather and aurora forecasting app for the Nordic skies — built with Kotlin and Jetpack Compose.

Norrsken (Swedish for *Northern Lights*) combines real-time weather data with live aurora tracking to help you plan around the weather and northern lights visibility. The app features reactive temperature-based colour coding, custom animated weather effects for rain and snow, and clean scrollable UI cards — all built natively with Jetpack Compose.

## Screenshots

<img width="1344" height="2992" alt="Screenshot_20260418-091841" src="https://github.com/user-attachments/assets/e9961092-22ef-4823-ae19-4af695b9287d" />

## Features

- **Hourly weather forecast** — up to 48 hours ahead with temperature, precipitation, and weather icons
- **Live Kp Index** — real-time geomagnetic activity data from NOAA for aurora borealis visibility
- **Sunrise & sunset times** — so you know exactly when darkness falls for aurora hunting
- **Reactive temperature colours** — temperature text shifts dynamically from cool blues to warm oranges
- **Custom weather effects** — animated rain and snow effects that respond to current conditions
- **Location-aware** — all data fetched for your current GPS location

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Networking | Retrofit2 |
| Image Loading | Coil3 |
| Architecture | MVVM |

## APIs

| API | Usage |
|---|---|
| [yr.no LocationForecast 2.0](https://api.met.no/weatherapi/locationforecast/2.0/) | Hourly weather forecast — temperature, precipitation, conditions |
| [yr.no Sunrise 3.0](https://api.met.no/weatherapi/sunrise/3.0/) | Sunrise and sunset times for current location |
| [NOAA Planetary K-Index](https://services.swpc.noaa.gov/json/planetary_k_index_1m.json) | Live geomagnetic activity for aurora forecasting |
| [MET Norway Weather Icons](https://github.com/metno/weathericons) | Weather condition symbol imagery |

## Getting Started

1. Clone the repository
```bash
git clone https://github.com/adam-398/norrsken-kotlin/
```
2. Open in Android Studio
3. Build and run on a device or emulator with location permissions enabled

> No API keys required — all data sources are open and free.
