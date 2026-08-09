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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.ui.components.SongListItem
import com.example.ui.theme.BusAmberPrimary
import com.example.ui.theme.BusBorderColor
import com.example.ui.theme.BusCardBackground
import com.example.ui.theme.BusPeachHighlight
import com.example.ui.theme.BusTextLight
import com.example.ui.theme.BusTextMuted

@Composable
fun LibraryScreen(
    viewModel: MusicViewModel,
    uiState: MusicUiState,
    isPlaying: Boolean,
    currentSong: Song?,
    modifier: Modifier = Modifier
) {
    val years = listOf("ALL", "1990", "1991", "1992", "1994", "1995", "1996", "1997", "1998", "1999")

    Column(
        modifier = modifier
            .testTag("library_screen")
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Title
        Text(
            text = "90s SONGS EXPRESS",
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            color = BusAmberPrimary
        )
        Text(
            text = "Search by song, singer, movie or year",
            fontSize = 12.sp,
            color = BusTextMuted,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            placeholder = { Text("Search 'Kumar Sanu', 'DDLJ', 'Aashiqui'...", color = BusTextMuted) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = BusAmberPrimary
                )
            },
            trailingIcon = {
                if (uiState.searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setSearchQuery("") }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = BusTextMuted)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_input"),
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = BusCardBackground,
                unfocusedContainerColor = BusCardBackground,
                focusedBorderColor = BusAmberPrimary,
                unfocusedBorderColor = BusBorderColor,
                focusedTextColor = BusTextLight,
                unfocusedTextColor = BusTextLight
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Year Filter Row
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(years) { yearStr ->
                val isSelected = (yearStr == "ALL" && uiState.searchQuery.isEmpty()) || uiState.searchQuery == yearStr
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) BusAmberPrimary else BusPeachHighlight)
                        .border(1.dp, if (isSelected) BusAmberPrimary else BusBorderColor, RoundedCornerShape(12.dp))
                        .clickable {
                            if (yearStr == "ALL") {
                                viewModel.setSearchQuery("")
                            } else {
                                viewModel.setSearchQuery(yearStr)
                            }
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (yearStr == "ALL") "All 90s Years" else yearStr,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else BusAmberPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Song Count Header
        Text(
            text = "Found ${uiState.songs.size} Evergreen Hits",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = BusTextMuted
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Songs List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
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
}

