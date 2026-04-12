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

data class RainDrop(val x: Float, val y: Float, val speed: Float, val dropLength: Int, val drift: Float)

@Preview(showBackground = true)
@Composable
fun LightRainEffect() {
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
        val rainEffect by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(4000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "rain"
        )


        val drops = remember {
            List(200) {
                RainDrop(
                    x = (0..1000).random() / 1000f,
                    y = (0..1000).random() / 1000f,
                    speed = (15..35).random() / 10f,
                    dropLength = (20..50).random(),
                    drift = (-5..15).random().toFloat()
                )
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            drops.forEach { drop ->
                val dropY = (rainEffect * drop.speed + drop.y) % 1f * size.height
                drawLine(
                    color = Color(0x55BDC1DC),
                    start = Offset(drop.x * size.width + (rainEffect * drop.drift), dropY),
                    end = Offset(drop.x * size.width + (rainEffect * drop.drift), dropY + drop.dropLength),
                    strokeWidth = 4f
                )
            }
        }
    }
}


