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
import kotlin.math.sin

data class SnowFlake(
    val x: Float,
    val y: Float,
    val speed: Float,
    val radius: Int,
    val drift: Float
)

@Preview(showBackground = true)
@Composable
fun LightSnowEffect() {

    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val snowEffect by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "snow"
    )


    val flakes = remember {
        List(500) {
            SnowFlake(
                x = (0..1000).random() / 1000f,
                y = (0..1000).random() / 1000f,
                speed = (10..15).random() / 10f,
                radius = (3..4).random(),
                drift = (-40..40).random().toFloat()
            )
        }
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        flakes.forEach { drop ->
            val dropY = (snowEffect * drop.speed + drop.y) % 1f * size.height
            drawCircle(
                color = Color(0xFFB6B9BD),
                radius = drop.radius.toFloat(),
                center = Offset(
                    drop.x * size.width + (sin(snowEffect * Math.PI * 2 + drop.drift) * 20f).toFloat(),
                    dropY
                )
            )
        }
    }

}


