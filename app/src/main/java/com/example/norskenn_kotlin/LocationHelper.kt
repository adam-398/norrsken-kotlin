package com.example.norskenn_kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationHelper(private val context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getLocation(): Pair<Double, Double> {
        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        continuation.resume(Pair(location.latitude, location.longitude))
                    } else {
                        // Fallback to Harads if location is null
                        continuation.resume(Pair(65.97, 21.07))
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
}

/**
 * Function which returns the users location name, which is then shown to them in the UI.
 * @param context The activity context.
 * @param lat The latitude of the location.
 * @param lon The longitude of the location.
 * @return The name of the location.
 */
fun getLocationName(context: Context, lat: Double, lon: Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses = geocoder.getFromLocation(lat, lon, 1)
    android.util.Log.d("LocationHelper", "addresses: $addresses")
    return addresses?.firstOrNull()?.locality
        ?: addresses?.firstOrNull()?.subLocality
        ?: addresses?.firstOrNull()?.subAdminArea
        ?: addresses?.firstOrNull()?.adminArea
        ?: "Unknown"
}