package ru.netology.mymusicplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.mymusicplayer.R
import ru.netology.mymusicplayer.databinding.SongCardBinding
import ru.netology.mymusicplayer.dto.Track
import ru.netology.mymusicplayer.utils.Utils


interface OnInteractionListener {
    fun onPlayPause(track: Track) {}
    fun onLike(track: Track) {}
}

class AlbumAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Track, TrackViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = SongCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)
        holder.bind(track)
    }
}

class TrackViewHolder(
    private val binding: SongCardBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track) {

        binding.apply {
            tvSongName.text = track.file
            tvSongDuration.text = Utils.formateMillis(track.duration)
            playPauseButtonChange(track)
            mbLike.isChecked = track.liked
            mbPlayPause.setOnClickListener {
                onInteractionListener.onPlayPause(track)
            }
            mbLike.setOnClickListener {
                onInteractionListener.onLike(track)
            }
        }
    }
}

private fun SongCardBinding.playPauseButtonChange(track: Track) {
    if (track.isPlayed) {
        mbPlayPause.setIconResource(R.drawable.ic_pause_button)
        mbPlayPause.setIconTintResource(R.color.red)
    } else {
        mbPlayPause.setIconResource(R.drawable.ic_play_black_and_white)
        mbPlayPause.setIconTintResource(R.color.light_green)
    }
}

class TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}
