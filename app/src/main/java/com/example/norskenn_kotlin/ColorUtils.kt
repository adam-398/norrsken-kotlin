package com.example.norskenn_kotlin

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

/**
 * Function which changes temperature text color based on the temperature.
 * Ranging from blue for cold to red for hot.
 */

fun ColorChangingText (temperature: Double): Color {
    val fraction = (temperature - (-15)) / (25 - (-15))
    val cold = Color(0xFF36CFF5)
    val neutral = Color(0xFFFFFFFF)
    val hot = Color(0xFFF55C36)

    return if (fraction < 0.375f) {
        lerp(cold, neutral, (fraction / 0.375f).toFloat())
    } else {
        lerp(neutral, hot, ((fraction - 0.375f) / 0.625f).toFloat())
    }
}

