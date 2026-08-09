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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Song
import com.example.data.db.PlaylistEntity
import com.example.ui.MusicUiState
import com.example.ui.MusicViewModel
import com.example.ui.components.SongListItem
import com.example.ui.theme.BusAmberPrimary
import com.example.ui.theme.BusBorderColor
import com.example.ui.theme.BusCardBackground
import com.example.ui.theme.BusPeachHighlight
import com.example.ui.theme.BusTextLight
import com.example.ui.theme.BusTextMuted

@Composable
fun FavoritesScreen(
    viewModel: MusicViewModel,
    uiState: MusicUiState,
    isPlaying: Boolean,
    currentSong: Song?,
    modifier: Modifier = Modifier
) {
    val favoriteSongs = viewModel.getFavoriteSongs()
    var showCreateDialog by remember { mutableStateOf(false) }
    var newPlaylistName by remember { mutableStateOf("") }

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = {
                Text(
                    text = "CREATE BUS PASS PLAYLIST",
                    fontWeight = FontWeight.Black,
                    color = BusAmberPrimary,
                    fontSize = 16.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = "Name your custom 90s cassette mixtape:",
                        fontSize = 12.sp,
                        color = BusTextLight
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newPlaylistName,
                        onValueChange = { newPlaylistName = it },
                        placeholder = { Text("e.g. Night Bus Cassette 1995") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BusAmberPrimary,
                            unfocusedBorderColor = BusBorderColor,
                            focusedTextColor = BusTextLight,
                            unfocusedTextColor = BusTextLight
                        ),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newPlaylistName.isNotBlank()) {
                            viewModel.createCustomPlaylist(newPlaylistName)
                            newPlaylistName = ""
                            showCreateDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BusAmberPrimary, contentColor = Color.White)
                ) {
                    Text("CREATE")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) {
                    Text("CANCEL", color = BusTextMuted)
                }
            },
            containerColor = BusCardBackground
        )
    }

    LazyColumn(
        modifier = modifier
            .testTag("favorites_screen")
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 100.dp)
    ) {
        // Bus Pass Membership Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(BusPeachHighlight)
                    .border(2.dp, BusAmberPrimary, RoundedCornerShape(24.dp))
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(BusAmberPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CardMembership,
                                contentDescription = "Pass",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "VIP PASSENGER PASS",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = BusAmberPrimary
                            )
                            Text(
                                text = "${favoriteSongs.size} Saved 90s Evergreen Songs",
                                fontSize = 12.sp,
                                color = BusTextMuted,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    if (favoriteSongs.isNotEmpty()) {
                        Button(
                            onClick = { viewModel.playSong(favoriteSongs.first()) },
                            colors = ButtonDefaults.buttonColors(containerColor = BusAmberPrimary, contentColor = Color.White),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play Pass", tint = Color.White)
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        // Playlists Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MY 90s MIXTAPES (${uiState.playlists.size})",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = BusAmberPrimary
                )
                Button(
                    onClick = { showCreateDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = BusAmberPrimary, contentColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "New", modifier = Modifier.size(16.dp), tint = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "NEW MIXTAPE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Playlist Items
        if (uiState.playlists.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(BusCardBackground)
                        .border(1.dp, BusBorderColor, RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No custom mixtapes created yet. Tap 'NEW MIXTAPE' above!",
                        fontSize = 12.sp,
                        color = BusTextMuted
                    )
                }
            }
        } else {
            items(uiState.playlists) { playlist ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(BusCardBackground)
                        .border(1.dp, BusBorderColor, RoundedCornerShape(16.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.QueueMusic,
                                contentDescription = "Playlist",
                                tint = BusAmberPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = playlist.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BusTextLight
                                )
                                Text(
                                    text = playlist.description,
                                    fontSize = 11.sp,
                                    color = BusTextMuted
                                )
                            }
                        }

                        IconButton(onClick = { viewModel.deletePlaylist(playlist.id) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = BusTextMuted)
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        // Saved Favorite Songs Header
        item {
            Text(
                text = "SAVED BUS PASS FAVORITES",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = BusAmberPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (favoriteSongs.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(BusCardBackground)
                        .border(1.dp, BusBorderColor, RoundedCornerShape(16.dp))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = BusTextMuted, modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No favorite 90s songs added yet.",
                            fontSize = 13.sp,
                            color = BusTextLight,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Tap the heart icon on any song to save it to your Bus Pass!",
                            fontSize = 11.sp,
                            color = BusTextMuted
                        )
                    }
                }
            }
        } else {
            items(favoriteSongs) { song ->
                val isSongPlaying = isPlaying && currentSong?.id == song.id
                SongListItem(
                    song = song,
                    isPlaying = isSongPlaying,
                    isFavorite = true,
                    onSongClick = { viewModel.playSong(song) },
                    onFavoriteToggle = { viewModel.toggleFavorite(song.id) },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

