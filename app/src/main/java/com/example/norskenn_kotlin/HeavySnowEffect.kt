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
fun HeavySnowEffect()

    {
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



