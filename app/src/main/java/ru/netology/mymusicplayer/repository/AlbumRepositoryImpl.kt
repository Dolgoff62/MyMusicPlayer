package ru.netology.mymusicplayer.repository

import android.media.MediaMetadataRetriever
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import ru.netology.mymusicplayer.BuildConfig
import ru.netology.mymusicplayer.api.AlbumApi
import ru.netology.mymusicplayer.dao.TrackDao
import ru.netology.mymusicplayer.dto.Album
import ru.netology.mymusicplayer.dto.Track
import ru.netology.mymusicplayer.entity.TrackEntity
import ru.netology.mymusicplayer.entity.toDto
import ru.netology.mymusicplayer.entity.toEntity
import ru.netology.mymusicplayer.exceptions.ApiException
import ru.netology.mymusicplayer.exceptions.NetworkException
import ru.netology.mymusicplayer.exceptions.UnknownException
import java.io.IOException


class AlbumRepositoryImpl(private val dao: TrackDao) : AlbumRepository {

    override val data = dao.getTracks().map(List<TrackEntity>::toDto)

    override suspend fun getAllWithDuration(): List<Track> {
        val tracksListWithDuration: List<Track> = coroutineScope {
            getAlbum().tracks.map {
                it.copy(duration = getDuration(BuildConfig.BASE_URL + it.file))
            }
            awaitAll()
        }
        dao.insertTracks(tracksListWithDuration.toEntity())
        return tracksListWithDuration
    }

    override suspend fun getAlbum(): Album {
        try {
            val response = AlbumApi.service.getAlbum()
            if (!response.isSuccessful) {
                throw ApiException(response.code(), response.message())
            }
            return response.body() ?: throw ApiException(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw  UnknownException
        }
    }

    override suspend fun insertTracks() {
        try {
            dao.insertTracks(getAlbum().tracks.toEntity())
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw  UnknownException
        }
    }

    override suspend fun isPlayed(id: Int) {
        try {
            dao.isPlayed(id)
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw  UnknownException
        }
    }

    override suspend fun likeById(id: Int) {
        try {
            dao.likeById(id)
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw  UnknownException
        }
    }

    override suspend fun getDuration(url: String): Long = withContext(Dispatchers.IO) {
        MediaMetadataRetriever().apply {
            setDataSource(url)
        }
            .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            ?.toLong()
            ?: 0
    }
}
