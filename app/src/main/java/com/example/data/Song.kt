package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey val id: String,
    val title: String,
    val movie: String,
    val singers: String,
    val year: Int,
    val streamUrl: String,
    val coverArtUrl: String = "",
    val durationSec: Int = 270,
    val category: String = "Romance 90s",
    val lyrics: String = "",
    val trivia: String = ""
)

enum class MusicCategory(val displayName: String, val tagLine: String) {
    ALL("Sabhi Gaane", "All 90s Evergreen Hits"),
    ROMANCE("Night Bus Romance", "Kumar Sanu & Alka Yagnik Magic"),
    HIGH_ENERGY("Front Seat Party", "Chaiyya Chaiyya & Bus Dance Beats"),
    SAD_HITS("Dhaba Stop Melodies", "Heartbreak & Emotional Classics"),
    MONSOON("Rain Bus Special", "Tip Tip Barsa & Monsoon Duets"),
    CHAI_STOP("Chai Break Duets", "Classic 90s Nostalgic Hits")
}
