package com.ericg.neatflix.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericg.neatflix.data.local.MyListMovie
import com.ericg.neatflix.data.repository.WatchListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(private val repo: WatchListRepository) : ViewModel() {

    private val _addedToWatchList = mutableStateOf(0)
    val addedToWatchList: State<Int> = _addedToWatchList

    private val _myWatchList = mutableStateOf(emptyList<MyListMovie>())
    val myWatchList: State<List<MyListMovie>> = _myWatchList

    init {
        getFullWatchList()
    }

    fun addToWatchList(movie: MyListMovie) {
        viewModelScope.launch {
            repo.addToWatchList(movie)
        }.invokeOnCompletion {
            exists(movie.mediaId)
        }
    }

    fun exists(mediaId: Int) {
        viewModelScope.launch {
            _addedToWatchList.value = repo.exists(mediaId)
        }
    }

    fun removeFromWatchList(mediaId: Int) {
        viewModelScope.launch {
            repo.removeFromWatchList(mediaId)
        }.invokeOnCompletion {
            exists(mediaId)
        }
    }

    fun getFullWatchList() = repo.getFullWatchList()

    fun searchInWatchList(searchParam: String): List<MyListMovie> {
        return repo.searchInWatchList(searchParam)
    }

    fun deleteWatchList() {
        viewModelScope.launch {
            repo.deleteWatchList()
        }
    }
}
