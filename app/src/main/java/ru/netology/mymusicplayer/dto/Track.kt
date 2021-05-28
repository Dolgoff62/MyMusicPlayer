package ru.netology.mymusicplayer.dto

data class Track(
    val id: Int,
    val file: String,
    val isPlayed: Boolean = false,
    val liked: Boolean = false,
    val duration: Long
)
