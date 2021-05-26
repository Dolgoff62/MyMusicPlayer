package ru.netology.mymusicplayer.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.mymusicplayer.model.AlbumModel
import ru.netology.mymusicplayer.repository.AlbumRepository
import ru.netology.mymusicplayer.repository.AlbumRepositoryImpl
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AlbumRepository = AlbumRepositoryImpl()

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
}