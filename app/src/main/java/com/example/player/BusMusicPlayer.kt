package com.example.player

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.ToneGenerator
import com.example.data.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BusMusicPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    private var progressUpdateJob: Job? = null

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _isBuffering = MutableStateFlow(false)
    val isBuffering: StateFlow<Boolean> = _isBuffering.asStateFlow()

    private val _currentPositionMs = MutableStateFlow(0L)
    val currentPositionMs: StateFlow<Long> = _currentPositionMs.asStateFlow()

    private val _durationMs = MutableStateFlow(1L)
    val durationMs: StateFlow<Long> = _durationMs.asStateFlow()

    private val _playbackSpeed = MutableStateFlow(1.0f)
    val playbackSpeed: StateFlow<Float> = _playbackSpeed.asStateFlow()

    private val _isRepeat = MutableStateFlow(false)
    val isRepeat: StateFlow<Boolean> = _isRepeat.asStateFlow()

    private val _isShuffle = MutableStateFlow(false)
    val isShuffle: StateFlow<Boolean> = _isShuffle.asStateFlow()

    private var playlist: List<Song> = emptyList()
    private var currentIndex = -1

    var onSongFinished: (() -> Unit)? = null

    init {
        initMediaPlayer()
    }

    private fun initMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setOnPreparedListener { mp ->
                _isBuffering.value = false
                _durationMs.value = mp.duration.toLong().coerceAtLeast(1L)
                mp.start()
                _isPlaying.value = true
                startProgressTracker()
            }
            setOnCompletionListener {
                _isPlaying.value = false
                if (_isRepeat.value) {
                    _currentSong.value?.let { playSong(it) }
                } else {
                    onSongFinished?.invoke()
                }
            }
            setOnErrorListener { _, _, _ ->
                _isBuffering.value = false
                _isPlaying.value = false
                true
            }
            setOnBufferingUpdateListener { _, percent ->
                // Buffer progress
            }
        }
    }

    fun setQueue(songs: List<Song>, startIndex: Int = 0) {
        playlist = songs
        currentIndex = startIndex
        if (playlist.isNotEmpty() && currentIndex in playlist.indices) {
            playSong(playlist[currentIndex])
        }
    }

    fun playSong(song: Song) {
        _currentSong.value = song
        _isBuffering.value = true
        _isPlaying.value = false
        _currentPositionMs.value = 0L

        try {
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(song.streamUrl)
            mediaPlayer?.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
            _isBuffering.value = false
            _isPlaying.value = false
        }
    }

    fun togglePlayPause() {
        mediaPlayer?.let { mp ->
            try {
                if (mp.isPlaying) {
                    mp.pause()
                    _isPlaying.value = false
                    stopProgressTracker()
                } else {
                    if (_currentSong.value != null && !_isBuffering.value) {
                        mp.start()
                        _isPlaying.value = true
                        startProgressTracker()
                    } else if (playlist.isNotEmpty()) {
                        playSong(playlist[0])
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _isPlaying.value = false
            }
        }
    }

    fun seekTo(positionMs: Long) {
        mediaPlayer?.let { mp ->
            try {
                mp.seekTo(positionMs.toInt())
                _currentPositionMs.value = positionMs
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun playNext() {
        if (playlist.isEmpty()) return
        if (_isShuffle.value) {
            currentIndex = (playlist.indices).random()
        } else {
            currentIndex = (currentIndex + 1) % playlist.size
        }
        playSong(playlist[currentIndex])
    }

    fun playPrevious() {
        if (playlist.isEmpty()) return
        currentIndex = if (currentIndex - 1 < 0) playlist.size - 1 else currentIndex - 1
        playSong(playlist[currentIndex])
    }

    fun toggleRepeat() {
        _isRepeat.value = !_isRepeat.value
    }

    fun toggleShuffle() {
        _isShuffle.value = !_isShuffle.value
    }

    fun setTapeSpeed(speed: Float) {
        _playbackSpeed.value = speed
        mediaPlayer?.let { mp ->
            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    mp.playbackParams = mp.playbackParams.setSpeed(speed)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun startProgressTracker() {
        stopProgressTracker()
        progressUpdateJob = scope.launch {
            while (_isPlaying.value) {
                try {
                    mediaPlayer?.let { mp ->
                        if (mp.isPlaying) {
                            _currentPositionMs.value = mp.currentPosition.toLong()
                        }
                    }
                } catch (e: Exception) {
                    // Ignore transient state exceptions
                }
                delay(500)
            }
        }
    }

    private fun stopProgressTracker() {
        progressUpdateJob?.cancel()
        progressUpdateJob = null
    }

    fun playBusHornEffect() {
        scope.launch(Dispatchers.IO) {
            try {
                val toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
                // Dual horn tone burst
                toneGen.startTone(ToneGenerator.TONE_DTMF_8, 180)
                delay(120)
                toneGen.startTone(ToneGenerator.TONE_DTMF_0, 240)
                delay(250)
                toneGen.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun release() {
        stopProgressTracker()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
