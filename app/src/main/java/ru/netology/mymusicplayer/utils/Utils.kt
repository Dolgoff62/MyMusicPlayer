package ru.netology.mymusicplayer.utils

import ru.netology.mymusicplayer.dto.Album
import ru.netology.mymusicplayer.dto.Song

class Utils {
    companion object {
        val emptyAlbum = Album(
            id = 0,
            title = "",
            subtitle = "",
            artist = "",
            published = "",
            genre = "",
            songs = emptyList()
        )

        val emptySong = Song(
            id = 0,
            file = "",
        )
    }
}