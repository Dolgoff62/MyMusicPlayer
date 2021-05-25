package ru.netology.mymusicplayer.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.netology.mymusicplayer.dto.Album
import java.util.concurrent.TimeUnit
import ru.netology.mymusicplayer.BuildConfig.BASE_URL


class AlbumRepositoryImpl : AlbumRepository {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()
    private val typeAlbumToken = object : TypeToken<Album>() {}


    override fun getAlbum(): Album {
        val request: Request = Request.Builder()
            .url(BASE_URL)
            .build()

        return client.newCall(request)
            .execute()
            .use { it.body?.string() }
            .let {
                gson.fromJson(it, typeAlbumToken.type)
            }
    }
}