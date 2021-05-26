package ru.netology.mymusicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.mymusicplayer.databinding.ActivityMainBinding
import ru.netology.mymusicplayer.mediaObserver.MediaLifecycleObserver

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val mediaObserver = MediaLifecycleObserver()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(mediaObserver)
    }
}