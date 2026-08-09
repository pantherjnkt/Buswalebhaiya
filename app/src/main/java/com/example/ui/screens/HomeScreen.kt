package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.MusicCategory
import com.example.data.Song
import com.example.ui.MusicUiState
import com.example.ui.MusicViewModel
import com.example.ui.components.BusHornButton
import com.example.ui.components.CassettePlayerCard
import com.example.ui.components.SongListItem
import com.example.ui.theme.BusAmberPrimary
import com.example.ui.theme.BusBorderColor
import com.example.ui.theme.BusCardBackground
import com.example.ui.theme.BusPeachHighlight
import com.example.ui.theme.BusTextLight
import com.example.ui.theme.BusTextMuted

@Composable
fun HomeScreen(
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
    LazyColumn(
        modifier = modifier
            .testTag("home_screen")
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 100.dp)
    ) {
        // Hero Banner Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(2.dp, BusBorderColor, RoundedCornerShape(24.dp))
            ) {
                // Hero Image
                AsyncImage(
                    model = R.drawable.img_bus_hero_1786276269067,
                    contentDescription = "Bus Hero",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Overlay gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    BusAmberPrimary.copy(alpha = 0.6f),
                                    BusTextLight.copy(alpha = 0.9f)
                                )
                            )
                        )
                )

                // Banner Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(BusPeachHighlight)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "90s EXPRESS BUS RADIO",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = BusAmberPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "BUS WALE BHAIYA",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )

                    Text(
                        text = "100% Evergreen 90s Bollywood Cassette Hits • Kumar Sanu, Udit, Alka",
                        fontSize = 11.sp,
                        color = BusPeachHighlight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Play Non-Stop Radio Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                if (uiState.songs.isNotEmpty()) {
                                    viewModel.playSong(uiState.songs.first())
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BusPeachHighlight,
                                contentColor = BusAmberPrimary
                            ),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Radio, contentDescription = null, tint = BusAmberPrimary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "START 90s STREAM", fontWeight = FontWeight.Black, fontSize = 12.sp, color = BusAmberPrimary)
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        // Cassette Player Card
        item {
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
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        // Category Filter Chips
        item {
            Column {
                Text(
                    text = "SELECT BUS ROUTE PLAYLIST",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = BusAmberPrimary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(MusicCategory.values()) { category ->
                        val isSelected = uiState.selectedCategory == category.displayName
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) BusAmberPrimary else BusPeachHighlight)
                                .border(
                                    1.dp,
                                    if (isSelected) BusAmberPrimary else BusBorderColor,
                                    RoundedCornerShape(16.dp)
                                )
                                .clickable { viewModel.selectCategory(category.displayName) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = category.displayName,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else BusAmberPrimary
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        // Song List Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "90s EVERGREEN TRACKS (${uiState.songs.size})",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = BusTextLight
                )
                Text(
                    text = "View All",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = BusAmberPrimary,
                    modifier = Modifier.clickable {
                        viewModel.selectTab(com.example.ui.BusTab.EXPRESS_SONGS)
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Song List
        items(uiState.songs) { song ->
            val isSongPlaying = isPlaying && currentSong?.id == song.id
            val isFav = uiState.favoriteSongIds.contains(song.id)
            SongListItem(
                song = song,
                isPlaying = isSongPlaying,
                isFavorite = isFav,
                onSongClick = { viewModel.playSong(song) },
                onFavoriteToggle = { viewModel.toggleFavorite(song.id) },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

