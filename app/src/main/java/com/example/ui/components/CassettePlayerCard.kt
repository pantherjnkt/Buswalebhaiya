package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Song
import com.example.ui.theme.BusAmberPrimary
import com.example.ui.theme.BusBorderColor
import com.example.ui.theme.BusCardBackground
import com.example.ui.theme.BusCrimsonSeat
import com.example.ui.theme.BusCyanNeon
import com.example.ui.theme.BusGoldAccent
import com.example.ui.theme.BusMahoganyBackground
import com.example.ui.theme.BusPeachHighlight
import com.example.ui.theme.BusSurfaceDark
import com.example.ui.theme.BusTapeBlack
import com.example.ui.theme.BusTextLight
import com.example.ui.theme.BusTextMuted

@Composable
fun CassettePlayerCard(
    currentSong: Song?,
    isPlaying: Boolean,
    isBuffering: Boolean,
    positionMs: Long,
    durationMs: Long,
    speed: Float,
    isFavorite: Boolean,
    onPlayPauseToggle: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onSeek: (Long) -> Unit,
    onSpeedChange: (Float) -> Unit,
    onFavoriteToggle: () -> Unit,
    onHornClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "tapeReel")
    val reelRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = (1800 / speed).toInt(),
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "reelRotate"
    )

    val effectiveRotation = if (isPlaying) reelRotation else 0f

    Box(
        modifier = modifier
            .testTag("cassette_player_card")
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BusPeachHighlight, BusCardBackground, BusSurfaceDark)
                )
            )
            .border(2.dp, BusBorderColor, RoundedCornerShape(28.dp))
            .padding(18.dp)
    ) {
        Column {
            // Header bar: "BUS WALE BHAIYA STEREO TAPE"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(BusAmberPrimary)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "90s EVERGREEN STEREO",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }

                // Counter digits
                val counterText = String.format("%03d", (positionMs / 1000) % 999)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(BusMahoganyBackground)
                        .border(1.dp, BusAmberPrimary, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "TAPE $counterText",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = BusAmberPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Cassette Window with Reels
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(BusMahoganyBackground)
                    .border(2.dp, BusAmberPrimary, RoundedCornerShape(20.dp))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Reel
                    CassetteReel(rotation = effectiveRotation)

                    // Middle Label & Magnetic Tape Window
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(120.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(24.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(BusPeachHighlight)
                                .border(1.dp, BusAmberPrimary, RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = currentSong?.movie ?: "BUS WALE BHAIYA",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = BusAmberPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Magnetic Tape Lines
                        Canvas(modifier = Modifier.fillMaxWidth().height(16.dp)) {
                            drawLine(
                                color = BusAmberPrimary,
                                start = Offset(0f, size.height / 2),
                                end = Offset(size.width, size.height / 2),
                                strokeWidth = 8f
                            )
                        }

                        Text(
                            text = if (isPlaying) "SIDE A - PLAYING" else "SIDE A - STOPPED",
                            fontSize = 8.sp,
                            color = BusTextMuted,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Right Reel
                    CassetteReel(rotation = effectiveRotation)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Song Info Box
            Column {
                Text(
                    text = currentSong?.title ?: "Select a 90s Song",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = BusAmberPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${currentSong?.singers ?: "Kumar Sanu, Alka Yagnik"} • ${currentSong?.year ?: 1995}",
                    fontSize = 13.sp,
                    color = BusTextMuted,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Equalizer Visualizer
            EqualizerVisualizer(isPlaying = isPlaying)

            // SeekBar Slider
            Slider(
                value = positionMs.toFloat(),
                onValueChange = { onSeek(it.toLong()) },
                valueRange = 0f..durationMs.toFloat().coerceAtLeast(1f),
                colors = SliderDefaults.colors(
                    thumbColor = BusAmberPrimary,
                    activeTrackColor = BusAmberPrimary,
                    inactiveTrackColor = BusBorderColor
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Duration Labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = formatTime(positionMs), fontSize = 11.sp, color = BusTextMuted, fontWeight = FontWeight.Bold)
                Text(text = formatTime(durationMs), fontSize = 11.sp, color = BusTextMuted, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Playback Speed Selector (0.8x, 1.0x, 1.25x)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Tape Speed: ", fontSize = 11.sp, color = BusTextMuted, fontWeight = FontWeight.Medium)
                    listOf(0.8f, 1.0f, 1.25f).forEach { speedVal ->
                        val isSelected = speed == speedVal
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 3.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) BusAmberPrimary else BusPeachHighlight)
                                .clickable { onSpeedChange(speedVal) }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${speedVal}x",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else BusAmberPrimary
                            )
                        }
                    }
                }

                // Favorite Toggle
                IconButton(onClick = onFavoriteToggle) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) BusCrimsonSeat else BusAmberPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Control Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onPrevious) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Previous",
                        tint = BusAmberPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(BusPeachHighlight)
                        .border(2.dp, BusAmberPrimary, RoundedCornerShape(24.dp))
                        .clickable { onPlayPauseToggle() },
                    contentAlignment = Alignment.Center
                ) {
                    if (isBuffering) {
                        CircularProgressIndicator(
                            color = BusAmberPrimary,
                            modifier = Modifier.size(28.dp),
                            strokeWidth = 3.dp
                        )
                    } else {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Play/Pause",
                            tint = BusAmberPrimary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                IconButton(onClick = onNext) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next",
                        tint = BusAmberPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bus Horn Button
            BusHornButton(
                onClick = onHornClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CassetteReel(rotation: Float) {
    Canvas(
        modifier = Modifier
            .size(52.dp)
            .rotate(rotation)
    ) {
        // Outer wheel
        drawCircle(
            color = Color(0xFF40230A),
            radius = size.width / 2f
        )
        drawCircle(
            color = BusGoldAccent,
            radius = size.width / 2f,
            style = Stroke(width = 3f)
        )

        // Center hub
        drawCircle(
            color = Color.Black,
            radius = size.width / 4f
        )

        // Reel spokes
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = size.width / 2.5f
        for (i in 0 until 6) {
            val angle = Math.toRadians((i * 60).toDouble())
            val endX = center.x + (radius * Math.cos(angle)).toFloat()
            val endY = center.y + (radius * Math.sin(angle)).toFloat()
            drawLine(
                color = BusAmberPrimary,
                start = center,
                end = Offset(endX, endY),
                strokeWidth = 3f
            )
        }
    }
}

fun formatTime(ms: Long): String {
    val totalSec = (ms / 1000).toInt()
    val min = totalSec / 60
    val sec = totalSec % 60
    return String.format("%02d:%02d", min, sec)
}
