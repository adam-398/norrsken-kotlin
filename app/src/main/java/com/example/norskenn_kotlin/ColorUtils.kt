package com.example.norskenn_kotlin

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

/**
 * Returns a colour based for the temp text based on the current temp.
 * Interpolates between three colours using lerp
 * Cold (blue) at -15 and below
 * neutral (white) at 0 and above
 * Hot (red) at 25 and above
 * Temps outside these ranges are clamped to the end colours by coerceIn
 */

fun ColorChangingText(temperature: Double): Color {
    val fraction = ((temperature - (-15)) / (25 - (-15))).toFloat().coerceIn(0f, 1f)
    val veryCold = Color(0xFF005EFF)  // deep blue at -15°C
    val cold = Color(0xFF3E7AE3)      // mid blue at -7°C
    val neutral = Color(0xFFFAFAFA)   // white at 0°C
    val warm = Color(0xFFFFB347)  // soft amber
    val hot = Color(0xFFEA380D)   // deep red

    return when {
        fraction < 0.25f -> lerp(veryCold, cold, (fraction / 0.25f))
        fraction < 0.375f -> lerp(cold, neutral, ((fraction - 0.25f) / 0.125f))
        fraction < 0.875f -> lerp(neutral, warm, ((fraction - 0.375f) / 0.5f))
        else -> lerp(warm, hot, ((fraction - 0.875f) / 0.125f))
    }
}
