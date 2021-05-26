package ru.netology.mymusicplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.mymusicplayer.R
import ru.netology.mymusicplayer.databinding.SongCardBinding
import ru.netology.mymusicplayer.dto.Track
import ru.netology.mymusicplayer.mediaObserver.MediaLifecycleObserver


interface OnInteractionListener {
    fun onPlayPause(track: Track) {}
}

private val mediaObserver = MediaLifecycleObserver()

class AlbumAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Track, SongViewHolder>(SongDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = SongCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position)
        holder.bind(song)
    }
}

class SongViewHolder(
    private val binding: SongCardBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track) {

        binding.apply {
            tvSongName.text = track.file
            tvSongDuration.text = mediaObserver.player?.duration.toString()
            ibPlayPause.setOnClickListener {
                onInteractionListener.onPlayPause(track)
            }
        }
    }
}

class SongDiffCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }
}
