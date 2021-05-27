package ru.netology.mymusicplayer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.mymusicplayer.api.AlbumApi
import ru.netology.mymusicplayer.dto.Album
import ru.netology.mymusicplayer.dto.Track
import ru.netology.mymusicplayer.exceptions.ApiException
import ru.netology.mymusicplayer.exceptions.NetworkException
import ru.netology.mymusicplayer.exceptions.UnknownException
import ru.netology.mymusicplayer.model.TrackModel
import java.io.IOException


class AlbumRepositoryImpl : AlbumRepository {
    private var tracksList: List<Track> = emptyList()
    override val data: LiveData<List<Track>> = getAlbum().tracks.map(tracksList)

    override suspend fun getAlbum(): Album {
        try {
            val response = AlbumApi.service.getAlbum()
            if (!response.isSuccessful) {
                throw ApiException(response.code(), response.message())
            }
            tracksList = response.body()?.tracks ?: emptyList()
            return response.body() ?: throw ApiException(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw  UnknownException
        }
    }

    override fun isPlayed(id: Int) {
        tracksList.map { if (it.id == id) it.copy(isPlayed = true) else it }
    }
}