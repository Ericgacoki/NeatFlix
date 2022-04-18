package com.ericg.neatflix.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ericg.neatflix.data.repository.MoviesRepository
import com.ericg.neatflix.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(val repository: MoviesRepository) : ViewModel() {
    private var _similarMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val similarMovies: State<Flow<PagingData<Movie>>> = _similarMovies

    fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            repository.getSimilarMovies(movieId).also {
                _similarMovies.value = it
            }
        }
    }
}
