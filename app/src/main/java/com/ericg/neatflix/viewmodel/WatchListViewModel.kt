package com.ericg.neatflix.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericg.neatflix.data.local.MyListMovie
import com.ericg.neatflix.data.repository.WatchListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(private val repo: WatchListRepository) : ViewModel() {

    private val _addedToWatchList = mutableStateOf(0)
    val addedToWatchList: State<Int> = _addedToWatchList

    private var _myWatchList = mutableStateListOf<MyListMovie>()
    val myWatchList: SnapshotStateList<MyListMovie> = _myWatchList

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

    fun removeFromWatchList(movie: MyListMovie) {
        viewModelScope.launch {
            repo.removeFromWatchList(movie)
        }.invokeOnCompletion {
            exists(movie.mediaId)
        }
    }

    fun getFullWatchList() {
        viewModelScope.launch() {
            repo.getFullWatchList().collect {
                it.forEach { myListMovie ->
                    _myWatchList.add(myListMovie)
                }
            }
        }
    }

    fun searchInWatchList(searchParam: String): Flow<List<MyListMovie>> {
        return repo.searchInWatchList(searchParam)
    }

    fun deleteWatchList() {
        viewModelScope.launch {
            repo.deleteWatchList()
        }
    }
}
