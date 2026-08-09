package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Song
import com.example.data.db.AppDatabase
import com.example.data.db.FavoriteEntity
import com.example.data.db.PlaylistEntity
import com.example.data.db.PlaylistItemEntity
import com.example.data.db.RecentSongEntity
import com.example.data.repository.SongsRepository
import com.example.player.BusMusicPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class BusTab(val title: String) {
    RADIO("Bhaiya ka Radio"),
    EXPRESS_SONGS("90s Songs Express"),
    BUS_PASS("Bus Pass & Playlists"),
    CASSETTE_PLAYER("Cassette Player")
}

data class MusicUiState(
    val songs: List<Song> = emptyList(),
    val favoriteSongIds: Set<String> = emptySet(),
    val recentSongIds: List<String> = emptyList(),
    val playlists: List<PlaylistEntity> = emptyList(),
    val selectedCategory: String = "Sabhi Gaane",
    val searchQuery: String = "",
    val activeTab: BusTab = BusTab.RADIO,
    val showLyricsBottomSheet: Boolean = false,
    val hornClickCount: Int = 0
)

class MusicViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SongsRepository()
    private val db = AppDatabase.getInstance(application)
    private val dao = db.songDao()

    val player = BusMusicPlayer(application)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Sabhi Gaane")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _activeTab = MutableStateFlow(BusTab.RADIO)
    val activeTab: StateFlow<BusTab> = _activeTab.asStateFlow()

    private val _showLyrics = MutableStateFlow(false)
    val showLyrics: StateFlow<Boolean> = _showLyrics.asStateFlow()

    private val _hornClickCount = MutableStateFlow(0)
    val hornClickCount: StateFlow<Int> = _hornClickCount.asStateFlow()

    val uiState: StateFlow<MusicUiState> = combine(
        combine(dao.getFavoriteSongIds(), dao.getRecentSongIds(), dao.getAllPlaylists()) { favs, recents, playlists ->
            Triple(favs, recents, playlists)
        },
        combine(_selectedCategory, _searchQuery, _activeTab) { category, query, tab ->
            Triple(category, query, tab)
        }
    ) { (favs, recents, playlists), (category, query, tab) ->
        val filteredSongs = if (query.isNotBlank()) {
            repository.searchSongs(query)
        } else {
            repository.getSongsByCategory(category)
        }

        MusicUiState(
            songs = filteredSongs,
            favoriteSongIds = favs.toSet(),
            recentSongIds = recents,
            playlists = playlists,
            selectedCategory = category,
            searchQuery = query,
            activeTab = tab,
            showLyricsBottomSheet = _showLyrics.value,
            hornClickCount = _hornClickCount.value
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MusicUiState(songs = repository.allSongs)
    )

    init {
        // Queue all 90s songs into player by default
        player.setQueue(repository.allSongs)
        player.onSongFinished = {
            player.playNext()
        }
    }

    fun selectTab(tab: BusTab) {
        _activeTab.value = tab
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun playSong(song: Song) {
        val currentList = uiState.value.songs.ifEmpty { repository.allSongs }
        val index = currentList.indexOfFirst { it.id == song.id }
        if (index != -1) {
            player.setQueue(currentList, index)
        } else {
            player.playSong(song)
        }

        // Add to recent
        viewModelScope.launch {
            dao.addRecentSong(RecentSongEntity(song.id))
        }
    }

    fun toggleFavorite(songId: String) {
        viewModelScope.launch {
            if (uiState.value.favoriteSongIds.contains(songId)) {
                dao.removeFavorite(songId)
            } else {
                dao.addFavorite(FavoriteEntity(songId))
            }
        }
    }

    fun triggerBusHorn() {
        _hornClickCount.value += 1
        player.playBusHornEffect()
    }

    fun toggleLyrics() {
        _showLyrics.value = !_showLyrics.value
    }

    fun createCustomPlaylist(name: String, description: String = "Bus Passengers 90s Choice") {
        viewModelScope.launch {
            if (name.isNotBlank()) {
                dao.createPlaylist(PlaylistEntity(name = name, description = description))
            }
        }
    }

    fun addSongToPlaylist(playlistId: Long, songId: String) {
        viewModelScope.launch {
            dao.addSongToPlaylist(PlaylistItemEntity(playlistId = playlistId, songId = songId))
        }
    }

    fun deletePlaylist(playlistId: Long) {
        viewModelScope.launch {
            dao.deletePlaylist(playlistId)
        }
    }

    fun getFavoriteSongs(): List<Song> {
        val favIds = uiState.value.favoriteSongIds
        return repository.allSongs.filter { favIds.contains(it.id) }
    }

    fun getRecentSongs(): List<Song> {
        val recentIds = uiState.value.recentSongIds
        return recentIds.mapNotNull { id -> repository.getSongById(id) }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}
