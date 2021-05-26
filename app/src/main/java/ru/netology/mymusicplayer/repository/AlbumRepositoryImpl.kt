package ru.netology.mymusicplayer.repository

import ru.netology.mymusicplayer.api.AlbumApi
import ru.netology.mymusicplayer.dto.Album
import ru.netology.mymusicplayer.exceptions.ApiException
import java.io.IOException
import ru.netology.mymusicplayer.exceptions.NetworkException
import ru.netology.mymusicplayer.exceptions.UnknownException


class AlbumRepositoryImpl : AlbumRepository {


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
}