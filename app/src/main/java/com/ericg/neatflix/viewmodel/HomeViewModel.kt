package com.ericg.neatflix.viewmodel

import androidx.compose.runtime.MutableState
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
    private var _movieGenres = mutableStateListOf(Genre(null, "All"))
    val movieGenres: SnapshotStateList<Genre> = _movieGenres

    var selectedGenre = mutableStateOf("All")

    private var _trendingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val trendingMoviesState: State<Flow<PagingData<Movie>>> = _trendingMovies

    private var _popularMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val popularMoviesState: State<Flow<PagingData<Movie>>> = _popularMovies

    private var _topRatedMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val topRatedMoviesState: State<Flow<PagingData<Movie>>> = _topRatedMovies

    private var _nowPlayingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val nowPlayingMoviesState: State<Flow<PagingData<Movie>>> = _nowPlayingMovies

    private var _upcomingMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val upcomingMoviesState: State<Flow<PagingData<Movie>>> = _upcomingMovies

    private var _backInTheDaysMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val backInTheDaysMoviesState: State<Flow<PagingData<Movie>>> = _backInTheDaysMovies

    private var _recommendedMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val recommendedMovies: MutableState<Flow<PagingData<Movie>>> = _recommendedMovies
    var randomMovieId: Int? = null

    init {
        refreshAll(null)
    }

    fun refreshAll(genreId: Int? = null) {
        if (movieGenres.size == 1) {
            getMoviesGenre()
        }
        if (genreId == null) {
            selectedGenre.value = "All"
        }
        getTrendingMovies(genreId)
        getPopularMovies(genreId)
        getTopRatedMovies(genreId)
        getNowPlayingMovies(genreId)
        getUpcomingMovies(genreId)
        getBackInTheDaysMovies(genreId)
        randomMovieId?.let { id -> getRecommendedMovies(movieId = id, genreId) }
    }

    fun filterBySetSelectedGenre(genre: Genre) {
        selectedGenre.value = genre.name
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

    private fun getPopularMovies(genreId: Int?) {
        viewModelScope.launch {
            _popularMovies.value = if (genreId != null) {
                moviesRepository.getPopularMovies().map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.getPopularMovies().cachedIn(viewModelScope)
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
                }.cachedIn(viewModelScope)
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
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.getNowPlayingMovies().cachedIn(viewModelScope)
            }
        }
    }

    fun getRecommendedMovies(movieId: Int, genreId: Int? = null) {
        viewModelScope.launch {
            _recommendedMovies.value = if (genreId != null) {
                moviesRepository.getRecommendedMovies(movieId).map { result ->
                    result.filter { movie -> movie.genreIds!!.contains(genreId) }
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.getRecommendedMovies(movieId).cachedIn(viewModelScope)
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
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.getUpcomingMovies().cachedIn(viewModelScope)
            }
        }
    }

    private fun getBackInTheDaysMovies(genreId: Int?) {
        viewModelScope.launch {
            _backInTheDaysMovies.value = if (genreId != null) {
                moviesRepository.getBackInTheDaysMovies().map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                moviesRepository.getBackInTheDaysMovies().cachedIn(viewModelScope)
            }
        }
    }
}