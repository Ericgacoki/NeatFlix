package com.ericg.neatflix.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ericg.neatflix.data.repository.FilmRepository
import com.ericg.neatflix.model.Cast
import com.ericg.neatflix.model.Film
import com.ericg.neatflix.util.FilmType
import com.ericg.neatflix.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(val repository: FilmRepository) : ViewModel() {
    private var _similarFilms = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val similarMovies: State<Flow<PagingData<Film>>> = _similarFilms

    private var _movieCast = mutableStateOf<List<Cast>>(emptyList())
    val movieCast: State<List<Cast>> = _movieCast

    fun getSimilarFilms(filmId: Int, filmType: FilmType) {
        viewModelScope.launch {
            repository.getSimilarFilms(filmId, filmType).also {
                _similarFilms.value = it
            }.cachedIn(viewModelScope)
        }
    }

    fun getFilmCast(filmId: Int, filmType: FilmType) {
        viewModelScope.launch {
            repository.getFilmCast(filmId = filmId, filmType).also {
                if (it is Resource.Success) {
                    _movieCast.value = it.data!!.castResult
                }
            }
        }
    }
}
