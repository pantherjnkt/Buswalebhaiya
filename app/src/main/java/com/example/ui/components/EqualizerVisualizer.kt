package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BusAmberDark
import com.example.ui.theme.BusAmberPrimary
import com.example.ui.theme.BusCrimsonSeat
import kotlin.random.Random

@Composable
fun EqualizerVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    barCount: Int = 16
) {
    val transition = rememberInfiniteTransition(label = "equalizer")

    val bar1 by transition.animateFloat(0.1f, 0.9f, infiniteRepeatable(tween(400, easing = LinearEasing), RepeatMode.Reverse), label = "b1")
    val bar2 by transition.animateFloat(0.2f, 0.75f, infiniteRepeatable(tween(350, easing = LinearEasing), RepeatMode.Reverse), label = "b2")
    val bar3 by transition.animateFloat(0.05f, 0.95f, infiniteRepeatable(tween(500, easing = LinearEasing), RepeatMode.Reverse), label = "b3")
    val bar4 by transition.animateFloat(0.3f, 0.8f, infiniteRepeatable(tween(300, easing = LinearEasing), RepeatMode.Reverse), label = "b4")

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(36.dp)
    ) {
        val barWidth = size.width / (barCount * 1.5f)
        val spacing = barWidth * 0.5f

        for (i in 0 until barCount) {
            val factor = when (i % 4) {
                0 -> bar1
                1 -> bar2
                2 -> bar3
                else -> bar4
            }

            val currentHeight = if (isPlaying) {
                size.height * (0.15f + factor * 0.8f)
            } else {
                size.height * 0.1f
            }

            val barColor = when (i % 3) {
                0 -> BusAmberPrimary
                1 -> BusCrimsonSeat
                else -> BusAmberDark
            }

            val x = i * (barWidth + spacing)
            val y = size.height - currentHeight

            drawRoundRect(
                color = barColor,
                topLeft = Offset(x, y),
                size = Size(barWidth, currentHeight),
                cornerRadius = CornerRadius(4f, 4f)
            )
        }
    }
}
