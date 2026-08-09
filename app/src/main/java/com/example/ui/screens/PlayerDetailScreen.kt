package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Song
import com.example.ui.MusicUiState
import com.example.ui.MusicViewModel
import com.example.ui.components.CassettePlayerCard
import com.example.ui.components.LyricsView
import com.example.ui.theme.BusAmberPrimary
import com.example.ui.theme.BusCardBackground
import com.example.ui.theme.BusMahoganyBackground
import com.example.ui.theme.BusPeachHighlight
import com.example.ui.theme.BusTextLight
import com.example.ui.theme.BusTextMuted

@Composable
fun PlayerDetailScreen(
    viewModel: MusicViewModel,
    uiState: MusicUiState,
    isPlaying: Boolean,
    isBuffering: Boolean,
    currentSong: Song?,
    positionMs: Long,
    durationMs: Long,
    speed: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .testTag("player_detail_screen")
            .fillMaxSize()
            .background(BusMahoganyBackground)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Screen Title Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "CASSETTE STEREO DECK",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = BusAmberPrimary
                )
                Text(
                    text = "Bus Conductor Special • 90s Hi-Fi Audio",
                    fontSize = 11.sp,
                    color = BusTextMuted,
                    fontWeight = FontWeight.Medium
                )
            }

            // Lyrics Toggle Button
            Button(
                onClick = { viewModel.toggleLyrics() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (uiState.showLyricsBottomSheet) BusAmberPrimary else BusPeachHighlight,
                    contentColor = if (uiState.showLyricsBottomSheet) Color.White else BusAmberPrimary
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(imageVector = Icons.Default.QueueMusic, contentDescription = "Lyrics")
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (uiState.showLyricsBottomSheet) "HIDE LYRICS" else "VIEW LYRICS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Large Cassette Player Deck
        CassettePlayerCard(
            currentSong = currentSong,
            isPlaying = isPlaying,
            isBuffering = isBuffering,
            positionMs = positionMs,
            durationMs = durationMs,
            speed = speed,
            isFavorite = currentSong?.let { uiState.favoriteSongIds.contains(it.id) } ?: false,
            onPlayPauseToggle = { viewModel.player.togglePlayPause() },
            onNext = { viewModel.player.playNext() },
            onPrevious = { viewModel.player.playPrevious() },
            onSeek = { viewModel.player.seekTo(it) },
            onSpeedChange = { viewModel.player.setTapeSpeed(it) },
            onFavoriteToggle = { currentSong?.let { viewModel.toggleFavorite(it.id) } },
            onHornClick = { viewModel.triggerBusHorn() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lyrics View
        LyricsView(
            song = currentSong,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}

