package com.ericg.neatflix.viewmodel

import androidx.compose.runtime.MutableState
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

    private val _watchList = mutableStateOf<Flow<List<MyListMovie>>>(emptyFlow())
    val watchList: MutableState<Flow<List<MyListMovie>>> = _watchList

    init {
        getWatchList()
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

    private fun getWatchList(){
       _watchList.value = repo.getFullWatchList()
    }

    fun deleteWatchList() {
        viewModelScope.launch {
            repo.deleteWatchList()
        }
    }
}
