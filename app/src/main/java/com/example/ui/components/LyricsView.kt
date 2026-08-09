package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
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
import com.example.ui.theme.BusAmberPrimary
import com.example.ui.theme.BusBorderColor
import com.example.ui.theme.BusCardBackground
import com.example.ui.theme.BusPeachHighlight
import com.example.ui.theme.BusTextLight
import com.example.ui.theme.BusTextMuted

@Composable
fun LyricsView(
    song: Song?,
    modifier: Modifier = Modifier
) {
    if (song == null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Select a 90s song to view lyrics", color = BusTextMuted)
        }
        return
    }

    Column(
        modifier = modifier
            .testTag("lyrics_view")
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Song Title Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = null,
                tint = BusAmberPrimary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = song.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = BusAmberPrimary
                )
                Text(
                    text = "Film: ${song.movie} (${song.year}) • ${song.singers}",
                    fontSize = 13.sp,
                    color = BusTextMuted,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 90s Trivia Box
        if (song.trivia.isNotBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(BusPeachHighlight)
                    .border(1.dp, BusAmberPrimary, RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Trivia",
                        tint = BusAmberPrimary,
                        modifier = Modifier.size(20.dp).padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "BHAIYA'S 90s TRIVIA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = BusAmberPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = song.trivia,
                            fontSize = 12.sp,
                            color = BusTextLight,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Lyrics Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(BusCardBackground)
                .border(1.dp, BusBorderColor, RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "LYRICS (90s EVERGREEN)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = BusAmberPrimary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = song.lyrics.ifBlank { "Lyrics loading for this evergreen classic..." },
                    fontSize = 15.sp,
                    color = BusTextLight,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

