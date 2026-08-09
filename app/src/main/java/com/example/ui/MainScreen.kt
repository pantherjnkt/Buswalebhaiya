package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.Song
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.PlayerDetailScreen
import com.example.ui.theme.BusAmberPrimary
import com.example.ui.theme.BusBorderColor
import com.example.ui.theme.BusCardBackground
import com.example.ui.theme.BusMahoganyBackground
import com.example.ui.theme.BusPeachHighlight
import com.example.ui.theme.BusSurfaceDark
import com.example.ui.theme.BusTextLight
import com.example.ui.theme.BusTextMuted

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MusicViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isPlaying by viewModel.player.isPlaying.collectAsState()
    val isBuffering by viewModel.player.isBuffering.collectAsState()
    val currentSong by viewModel.player.currentSong.collectAsState()
    val positionMs by viewModel.player.currentPositionMs.collectAsState()
    val durationMs by viewModel.player.durationMs.collectAsState()
    val speed by viewModel.player.playbackSpeed.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(BusPeachHighlight)
                                .border(1.dp, BusAmberPrimary, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🚌", fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "BUS WALE BHAIYA",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                color = BusAmberPrimary,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Only 90s Bollywood Evergreen Streaming",
                                fontSize = 10.sp,
                                color = BusTextMuted,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                },
                actions = {
                    // Quick Horn trigger action
                    IconButton(onClick = { viewModel.triggerBusHorn() }) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = "Bus Horn",
                            tint = BusAmberPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BusMahoganyBackground,
                    titleContentColor = BusTextLight
                )
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(BusMahoganyBackground)
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                // Mini Player (Visible when a song is loaded and user is not on Cassette Player tab)
                if (currentSong != null && uiState.activeTab != BusTab.CASSETTE_PLAYER) {
                    MiniPlayerBar(
                        song = currentSong!!,
                        isPlaying = isPlaying,
                        onPlayPause = { viewModel.player.togglePlayPause() },
                        onNext = { viewModel.player.playNext() },
                        onBarClick = { viewModel.selectTab(BusTab.CASSETTE_PLAYER) }
                    )
                }

                // Bottom Navigation Bar
                NavigationBar(
                    containerColor = BusSurfaceDark,
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        selected = uiState.activeTab == BusTab.RADIO,
                        onClick = { viewModel.selectTab(BusTab.RADIO) },
                        icon = { Icon(imageVector = Icons.Default.Radio, contentDescription = "Radio") },
                        label = { Text(text = "Radio", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BusAmberPrimary,
                            selectedTextColor = BusAmberPrimary,
                            indicatorColor = BusPeachHighlight,
                            unselectedIconColor = BusTextMuted,
                            unselectedTextColor = BusTextMuted
                        ),
                        modifier = Modifier.testTag("nav_radio")
                    )

                    NavigationBarItem(
                        selected = uiState.activeTab == BusTab.EXPRESS_SONGS,
                        onClick = { viewModel.selectTab(BusTab.EXPRESS_SONGS) },
                        icon = { Icon(imageVector = Icons.Default.LibraryMusic, contentDescription = "Songs") },
                        label = { Text(text = "90s Songs", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BusAmberPrimary,
                            selectedTextColor = BusAmberPrimary,
                            indicatorColor = BusPeachHighlight,
                            unselectedIconColor = BusTextMuted,
                            unselectedTextColor = BusTextMuted
                        ),
                        modifier = Modifier.testTag("nav_songs")
                    )

                    NavigationBarItem(
                        selected = uiState.activeTab == BusTab.BUS_PASS,
                        onClick = { viewModel.selectTab(BusTab.BUS_PASS) },
                        icon = { Icon(imageVector = Icons.Default.CardMembership, contentDescription = "Bus Pass") },
                        label = { Text(text = "Bus Pass", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BusAmberPrimary,
                            selectedTextColor = BusAmberPrimary,
                            indicatorColor = BusPeachHighlight,
                            unselectedIconColor = BusTextMuted,
                            unselectedTextColor = BusTextMuted
                        ),
                        modifier = Modifier.testTag("nav_bus_pass")
                    )

                    NavigationBarItem(
                        selected = uiState.activeTab == BusTab.CASSETTE_PLAYER,
                        onClick = { viewModel.selectTab(BusTab.CASSETTE_PLAYER) },
                        icon = { Icon(imageVector = Icons.Default.GraphicEq, contentDescription = "Cassette Deck") },
                        label = { Text(text = "Cassette", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BusAmberPrimary,
                            selectedTextColor = BusAmberPrimary,
                            indicatorColor = BusPeachHighlight,
                            unselectedIconColor = BusTextMuted,
                            unselectedTextColor = BusTextMuted
                        ),
                        modifier = Modifier.testTag("nav_cassette")
                    )
                }
            }
        },
        containerColor = BusMahoganyBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.activeTab) {
                BusTab.RADIO -> HomeScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    isPlaying = isPlaying,
                    isBuffering = isBuffering,
                    currentSong = currentSong,
                    positionMs = positionMs,
                    durationMs = durationMs,
                    speed = speed
                )
                BusTab.EXPRESS_SONGS -> LibraryScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    isPlaying = isPlaying,
                    currentSong = currentSong
                )
                BusTab.BUS_PASS -> FavoritesScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    isPlaying = isPlaying,
                    currentSong = currentSong
                )
                BusTab.CASSETTE_PLAYER -> PlayerDetailScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    isPlaying = isPlaying,
                    isBuffering = isBuffering,
                    currentSong = currentSong,
                    positionMs = positionMs,
                    durationMs = durationMs,
                    speed = speed
                )
            }
        }
    }
}

@Composable
fun MiniPlayerBar(
    song: Song,
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onBarClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("mini_player_bar")
            .clickable { onBarClick() }
            .background(BusPeachHighlight)
            .border(1.dp, BusBorderColor, RoundedCornerShape(0.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(BusAmberPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = song.title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BusTextLight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${song.singers} • ${song.movie}",
                        fontSize = 11.sp,
                        color = BusTextMuted,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onPlayPause) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pause",
                        tint = BusAmberPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                IconButton(onClick = onNext) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next",
                        tint = BusAmberPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

