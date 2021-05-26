package ru.netology.mymusicplayer.api

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import ru.netology.mymusicplayer.BuildConfig.BASE_URL
import ru.netology.mymusicplayer.dto.Album
import java.util.concurrent.TimeUnit

interface AlbumApi {
    @GET("album.json")
    suspend fun getAlbum(): Response<Album>

    companion object {

        private val okhttp = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okhttp)
            .build()

        val service: AlbumApi by lazy {
            retrofit.create(AlbumApi::class.java)
        }
    }
}

