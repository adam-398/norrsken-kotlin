package com.example.norskenn_kotlin

import android.R.attr.strokeWidth
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.cos
import kotlin.math.sin

data class SunRay(val angle: Float, val length: Float, val alpha: Float)

@Preview(showBackground = true)
@Composable
fun SunRayEffect() {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
        val infiniteTransition = rememberInfiniteTransition(label = "infinite")
        val sunRays by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(4000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "sun"
        )
        val rays = remember {
            List(20) { index ->
                SunRay(
                    angle = (index * 360f / 20),
                    length = (300..500).random().toFloat(),
                    alpha = (3..8).random() / 10f
                )
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sunX = size.width / 2
            val sunY = 0f
            rays.forEach { ray ->
                val animatedAngle = ray.angle + (sunRays * 180f)
                val endX = sunX + cos(Math.toRadians(animatedAngle.toDouble())).toFloat() * ray.length
                val endY = sunY + sin(Math.toRadians(animatedAngle.toDouble())).toFloat() * ray.length
                drawLine(
                    color = Color(0x55FFE484),
                    start = Offset(sunX, sunY),
                    end = Offset(endX, endY),
                    strokeWidth = 6f
                )
            }
        }
    }
}


