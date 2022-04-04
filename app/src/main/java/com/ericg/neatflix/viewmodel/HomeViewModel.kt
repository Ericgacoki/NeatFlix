package com.ericg.neatflix.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.ericg.neatflix.data.repository.GenreRepository
import com.ericg.neatflix.data.repository.MoviesRepository
import com.ericg.neatflix.model.Genre
import com.ericg.neatflix.model.Movie
import com.ericg.neatflix.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val genreRepository: GenreRepository
) : ViewModel() {

    var index = 0
    var offset = 0

    private var _movieGenres = mutableStateListOf(Genre(null, "All"))
    val movieGenres: SnapshotStateList<Genre> = _movieGenres

    var selectedGenre = mutableStateOf("All")

    private var _trendingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val trendingMoviesState: State<Flow<PagingData<Movie>>> = _trendingMovies

    private var _topRatedMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val topRatedMoviesState: State<Flow<PagingData<Movie>>> = _topRatedMovies

    private var _nowPlayingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val nowPlayingMoviesState: State<Flow<PagingData<Movie>>> = _nowPlayingMovies

    private var _upcomingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val upcomingMoviesState: State<Flow<PagingData<Movie>>> = _upcomingMovies

    init {
        refreshAll(null)
    }

    fun refreshAll(genreId: Int? = null) {
        if (movieGenres.size == 1){getMoviesGenre()}
        getTrendingMovies(genreId)
        getTopRatedMovies(genreId)
        getNowPlayingMovies(genreId)
        getUpcomingMovies(genreId)
    }

    fun setSelectedGenre(genre: Genre) {
        selectedGenre = mutableStateOf(genre.name)
        refreshAll(genre.id)
    }

    private fun getMoviesGenre() {
        viewModelScope.launch {
            when (val results = genreRepository.getMoviesGenre()) {
                is Resource.Success -> {
                    results.data?.genres?.forEach {
                        _movieGenres.add(it)
                    }
                }
                is Resource.Error -> {
                    Timber.e("Error loading Genres")
                }
                else -> {

                }
            }
        }
    }

    private fun getTrendingMovies(genreId: Int?) {
        viewModelScope.launch {
            _trendingMovies.value = if (genreId != null) {
                moviesRepository.getTrendingMovies().map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.getTrendingMovies().cachedIn(viewModelScope)
            }
        }
    }

    private fun getTopRatedMovies(genreId: Int?) {
        viewModelScope.launch {
            _topRatedMovies.value = if (genreId != null) {
                moviesRepository.getTopRatedMovies().map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }

                }
            } else {
                moviesRepository.getTopRatedMovies().cachedIn(viewModelScope)
            }
        }
    }

    private fun getNowPlayingMovies(genreId: Int?) {
        viewModelScope.launch {
            _nowPlayingMovies.value = if (genreId != null) {
                moviesRepository.getNowPlayingMovies().map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }
                }
            } else {
                moviesRepository.getNowPlayingMovies().cachedIn(viewModelScope)
            }
        }
    }

    private fun getUpcomingMovies(genreId: Int?) {
        viewModelScope.launch {
            _upcomingMovies.value = if (genreId != null) {
                moviesRepository.getUpcomingMovies().map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }
                }
            } else {
                moviesRepository.getUpcomingMovies().cachedIn(viewModelScope)
            }
        }
    }
}