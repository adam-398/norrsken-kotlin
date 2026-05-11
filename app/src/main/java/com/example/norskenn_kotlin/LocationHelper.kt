package com.example.norskenn_kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Build
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Location helper function, gets the users location from the device.
 */
class LocationHelper(private val context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getLocation(): Pair<Double, Double> {
        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
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
suspend fun getLocationName(context: Context, lat: Double, lon: Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        suspendCancellableCoroutine { continuation ->
            geocoder.getFromLocation(lat, lon, 1) { addresses ->
                val name = addresses.firstOrNull()?.locality
                    ?: addresses.firstOrNull()?.subLocality
                    ?: addresses.firstOrNull()?.subAdminArea
                    ?: addresses.firstOrNull()?.adminArea
                    ?: "Unknown"
                continuation.resume(name)
            }
        }
    } else {
        @Suppress("DEPRECATION")
        val addresses = geocoder.getFromLocation(lat, lon, 1)
        addresses?.firstOrNull()?.locality
            ?: addresses?.firstOrNull()?.subLocality
            ?: addresses?.firstOrNull()?.subAdminArea
            ?: addresses?.firstOrNull()?.adminArea
            ?: "Unknown"
    }
}