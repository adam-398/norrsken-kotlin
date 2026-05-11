package com.example.norskenn_kotlin

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

data class RainDrop(val x: Float, val y: Float, val speed: Float, val dropLength: Int, val drift: Float)

@Preview(showBackground = true)
@Composable
fun LightRainEffect()

    {
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


