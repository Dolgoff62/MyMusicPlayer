package ru.netology.mymusicplayer.ui

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import ru.netology.mymusicplayer.BuildConfig.BASE_URL
import ru.netology.mymusicplayer.adapter.AlbumAdapter
import ru.netology.mymusicplayer.adapter.OnInteractionListener
import ru.netology.mymusicplayer.databinding.ActivityMainBinding
import ru.netology.mymusicplayer.dto.Track
import ru.netology.mymusicplayer.mediaObserver.MediaLifecycleObserver
import ru.netology.mymusicplayer.viewModel.AlbumViewModel
import androidx.activity.viewModels
import androidx.core.view.isVisible


class MainActivity : AppCompatActivity() {
    private val viewModel: AlbumViewModel by viewModels()
    private val mediaObserver = MediaLifecycleObserver()
    private var playList: List<Track> = emptyList()
    private var currentIndex = 0
    private var currentTrack = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(mediaObserver)

        val adapter = AlbumAdapter(object : OnInteractionListener {
            override fun onPlayPause(track: Track) {
                playerController(BASE_URL + track.file)
                currentTrack = track.id
            }
        })


        binding.rvSongList.adapter = adapter

        binding.rvSongList.addItemDecoration(
            DividerItemDecoration(binding.rvSongList.context, DividerItemDecoration.VERTICAL),
        )

        viewModel.album.observe(this) { state ->

            binding.progressBarView.isVisible = state.loading
            playList = state.album.tracks

            binding.apply {
                tvAlbumName.text = state.album.title
                tvAuthorName.text = state.album.artist
                tvGenreLabel.text = state.album.genre
            }

            adapter.submitList(playList)

            playList.forEachIndexed { index, track ->
                if (track.id == currentTrack) currentIndex = index
            }
        }
    }

    fun playerController(url: String) {

        val nextListener =
            MediaPlayer.OnCompletionListener {
                mediaObserver.apply {
                    onStop()
                    if (currentIndex <= playList.size) {
                        currentIndex++
                        player?.setDataSource(BASE_URL + playList[currentIndex].file)
                        onPlay()
                    } else {
                        player?.setDataSource(BASE_URL + playList[0].file)
                        onPlay()
                    }
                }
            }

        mediaObserver.apply {
            if (player != null && player?.isPlaying == true) {
                if (currentTrack != currentIndex + 1) {
                    onStop()
                    player?.setDataSource(url)
                    onPlay()
                } else {
                    onStop()
                }
            } else {
                player?.setOnCompletionListener(nextListener)
                player?.setDataSource(url)
                onPlay()
            }
        }
    }
}