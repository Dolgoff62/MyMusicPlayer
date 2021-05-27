package ru.netology.mymusicplayer.viewModel

import android.app.Application
import androidx.lifecycle.*
import ru.netology.mymusicplayer.model.AlbumModel
import ru.netology.mymusicplayer.repository.AlbumRepository
import ru.netology.mymusicplayer.repository.AlbumRepositoryImpl
import kotlinx.coroutines.launch
import ru.netology.mymusicplayer.model.TrackModel

class AlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AlbumRepository = AlbumRepositoryImpl()

    val data: LiveData<TrackModel> = repository.data.map(::TrackModel)

    private val _album = MutableLiveData(AlbumModel())
    val album: LiveData<AlbumModel>
        get() = _album

    init {
        loadAlbum()
    }

    private fun loadAlbum() = viewModelScope.launch {
        try {
            _album.value = AlbumModel(loading = true)
            _album.value = AlbumModel(repository.getAlbum())

        } catch (e: Exception) {
            _album.value = AlbumModel(error = true)
        }
    }

    fun isPlayed(id: Int) = viewModelScope.launch {
        try {
            repository.isPlayed(id)
        } catch (e: Exception) {
            _album.value = AlbumModel(error = true)
        }
    }
}