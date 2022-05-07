package com.ericg.neatflix.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ericg.neatflix.data.repository.MoviesRepository
import com.ericg.neatflix.model.Cast
import com.ericg.neatflix.model.Movie
import com.ericg.neatflix.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(val repository: MoviesRepository) : ViewModel() {
    private var _similarMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val similarMovies: State<Flow<PagingData<Movie>>> = _similarMovies

    private var _movieCast = mutableStateOf<List<Cast>>(emptyList())
    val movieCast: State<List<Cast>> = _movieCast

    fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            repository.getSimilarMovies(movieId).also {
                _similarMovies.value = it
            }.cachedIn(viewModelScope)
        }
    }

    fun getMovieCast(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieCast(movieId = movieId).also {
                if (it is Resource.Success) {
                    _movieCast.value = it.data!!.castResult
                }
            }
        }
    }
}
