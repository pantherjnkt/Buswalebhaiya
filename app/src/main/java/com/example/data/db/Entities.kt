package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val songId: String,
    val addedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "recent_songs")
data class RecentSongEntity(
    @PrimaryKey val songId: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "custom_playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String = "",
    val createdTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "playlist_items", primaryKeys = ["playlistId", "songId"])
data class PlaylistItemEntity(
    val playlistId: Long,
    val songId: String
)
