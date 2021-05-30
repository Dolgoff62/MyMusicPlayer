package ru.netology.mymusicplayer.repository

import androidx.lifecycle.MutableLiveData
import ru.netology.mymusicplayer.api.AlbumApi
import ru.netology.mymusicplayer.dto.Album
import ru.netology.mymusicplayer.dto.Track
import ru.netology.mymusicplayer.exceptions.ApiException
import ru.netology.mymusicplayer.exceptions.NetworkException
import ru.netology.mymusicplayer.exceptions.UnknownException
import java.io.IOException


class AlbumRepositoryImpl : AlbumRepository {

     private var tracks: List<Track> = emptyList()

    override var data = MutableLiveData(tracks)


//    override suspend fun getAllWithDuration(): List<Track> {
//        val tracksListWithDuration: List<Track> = coroutineScope {
//            getAlbum().tracks.map {
//                it.copy(duration = getDuration(BuildConfig.BASE_URL + it.file))
//            }
//            awaitAll()
//        }
//        return tracksListWithDuration
//    }
//
    override suspend fun getAlbum(): Album {
        try {
            val response = AlbumApi.service.getAlbum()
            if (!response.isSuccessful) {
                throw ApiException(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiException(response.code(), response.message())
            tracks = body.tracks

            return body
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw  UnknownException
        }
    }

    override fun isPlayed(id: Int) {
        try {
            data.value?.map {
                if (it.id == id) it.isPlayed.not()
            }
        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw  UnknownException
        }
    }

//    override fun likeById(id: Int) {
//        try {
//            dao.likeById(id)
//        } catch (e: IOException) {
//            throw NetworkException
//        } catch (e: Exception) {
//            throw  UnknownException
//        }
//    }

//    override suspend fun getDuration(url: String): Long = withContext(Dispatchers.IO) {
//        MediaMetadataRetriever().apply {
//            setDataSource(url)
//        }
//            .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
//            ?.toLong()
//            ?: 0
//    }
}
