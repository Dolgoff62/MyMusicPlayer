package ru.netology.mymusicplayer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.mymusicplayer.dto.Album
import ru.netology.mymusicplayer.dto.Track

interface AlbumRepository {
    val data: LiveData<List<Track>>
    suspend fun getAlbum(): Album
    fun isPlayed(id: Int)
//    fun likeById(id: Int)
//    fun getDuration(url: String): Long
//    fun getAllWithDuration(): List<Track>
}
