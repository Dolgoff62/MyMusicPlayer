package ru.netology.mymusicplayer.repository

import androidx.lifecycle.LiveData
import ru.netology.mymusicplayer.dto.Album
import ru.netology.mymusicplayer.dto.Track

interface AlbumRepository {
    val data: LiveData<List<Track>>
    suspend fun getAlbum(): Album
    suspend fun insertTracks()
    suspend fun isPlayed(id: Int)
    suspend fun likeById(id: Int)
    suspend fun getDuration(url: String): Long
    suspend fun getAllWithDuration(): List<Track>
}
