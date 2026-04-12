package com.example.norskenn_kotlin


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
import kotlin.math.sin

@Preview(showBackground = true)
@Composable
fun HeavySnowEffect() {
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
        val snowEffect by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(4500, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "snow"
        )
        val flakes = remember {
            List(700) {
                SnowFlake(
                    x = (0..1000).random() / 1000f,
                    y = (0..1000).random() / 1000f,
                    speed = (10..25).random() / 10f,
                    radius = (4..5).random(),
                    drift = (-50..50).random().toFloat()
                )
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            flakes.forEach { drop ->
                val dropY = (snowEffect * drop.speed + drop.y) % 1f * size.height
                drawCircle(
                    color = Color(0xFFD5D9DC),
                    radius = drop.radius.toFloat(),
                    center = Offset(
                        drop.x * size.width + (sin(snowEffect * Math.PI * 2 + drop.drift) * 20f).toFloat(),
                        dropY
                    )
                )
            }
        }
    }
}


