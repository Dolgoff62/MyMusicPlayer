package ru.netology.mymusicplayer.repository

import ru.netology.mymusicplayer.dto.Album

interface AlbumRepository {
    suspend fun getAlbum(): Album
}
