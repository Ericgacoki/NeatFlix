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
import com.ericg.neatflix.data.repository.FilmRepository
import com.ericg.neatflix.model.Genre
import com.ericg.neatflix.model.Film
import com.ericg.neatflix.util.FilmType
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
    private val filmRepository: FilmRepository,
    private val genreRepository: GenreRepository
) : ViewModel() {
    private var _filmGenres = mutableStateListOf(Genre(null, "All"))
    val filmGenres: SnapshotStateList<Genre> = _filmGenres

    var selectedGenre: MutableState<Genre> = mutableStateOf(Genre(null, "All"))
    var selectedFilmType: MutableState<FilmType> = mutableStateOf(FilmType.MOVIE)

    private var _trendingMovies = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val trendingMoviesState: State<Flow<PagingData<Film>>> = _trendingMovies

    private var _popularFilms = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val popularFilmsState: State<Flow<PagingData<Film>>> = _popularFilms

    private var _topRatedFilm = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val topRatedFilmState: State<Flow<PagingData<Film>>> = _topRatedFilm

    private var _nowPlayingFilm = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val nowPlayingMoviesState: State<Flow<PagingData<Film>>> = _nowPlayingFilm

    private var _upcomingFilms = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val upcomingMoviesState: State<Flow<PagingData<Film>>> = _upcomingFilms

    private var _backInTheDaysFilms = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val backInTheDaysMoviesState: State<Flow<PagingData<Film>>> = _backInTheDaysFilms

    private var _recommendedFilms = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val recommendedMovies: MutableState<Flow<PagingData<Film>>> = _recommendedFilms
    var randomMovieId: Int? = null

    init {
        refreshAll()
    }

    fun refreshAll(
        genreId: Int? = selectedGenre.value.id,
        filmType: FilmType = selectedFilmType.value
    ) {
        if (filmGenres.size == 1) {
            getFilmGenre(selectedFilmType.value)
        }
        if (genreId == null) {
            selectedGenre.value = Genre(null, "All")
        }
        getTrendingFilms(genreId, filmType)
        getPopularFilms(genreId, filmType)
        getTopRatedFilm(genreId, filmType)
        getNowPlayingFilms(genreId, filmType)
        getUpcomingFilms(genreId)
        getBackInTheDaysFilms(genreId, filmType)
        randomMovieId?.let { id -> getRecommendedFilms(movieId = id, genreId, filmType) }
    }

    fun filterBySetSelectedGenre(genre: Genre) {
        selectedGenre.value = genre
        refreshAll(genre.id)
    }

    fun getFilmGenre(filmType: FilmType = selectedFilmType.value) {
        viewModelScope.launch {
            val defaultGenre = Genre(null, "All")
            when (val results = genreRepository.getMoviesGenre(filmType)) {
                is Resource.Success -> {
                    _filmGenres.clear()
                    _filmGenres.add(defaultGenre)
                    selectedGenre.value = defaultGenre
                    results.data?.genres?.forEach {
                        _filmGenres.add(it)
                    }
                }
                is Resource.Error -> {
                    Timber.e("Error loading Genres")
                }
                else -> { }
            }
        }
    }

    private fun getTrendingFilms(genreId: Int?, filmType: FilmType) {
        viewModelScope.launch {
            _trendingMovies.value = if (genreId != null) {
                filmRepository.getTrendingFilms(filmType).map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmRepository.getTrendingFilms(filmType).cachedIn(viewModelScope)
            }
        }
    }

    private fun getPopularFilms(genreId: Int?, filmType: FilmType) {
        viewModelScope.launch {
            _popularFilms.value = if (genreId != null) {
                filmRepository.getPopularFilms(filmType).map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmRepository.getPopularFilms(filmType).cachedIn(viewModelScope)
            }
        }
    }

    private fun getTopRatedFilm(genreId: Int?, filmType: FilmType) {
        viewModelScope.launch {
            _topRatedFilm.value = if (genreId != null) {
                filmRepository.getTopRatedFilm(filmType).map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmRepository.getTopRatedFilm(filmType).cachedIn(viewModelScope)
            }
        }
    }

    private fun getNowPlayingFilms(genreId: Int?, filmType: FilmType) {
        viewModelScope.launch {
            _nowPlayingFilm.value = if (genreId != null) {
                filmRepository.getNowPlayingFilms(filmType).map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmRepository.getNowPlayingFilms(filmType).cachedIn(viewModelScope)
            }
        }
    }

    fun getRecommendedFilms(movieId: Int, genreId: Int? = null, filmType: FilmType = selectedFilmType.value) {
        viewModelScope.launch {
            _recommendedFilms.value = if (genreId != null) {
                filmRepository.getRecommendedFilms(movieId, filmType).map { result ->
                    result.filter { movie -> movie.genreIds!!.contains(genreId) }
                }.cachedIn(viewModelScope)
            } else {
                filmRepository.getRecommendedFilms(movieId, filmType).cachedIn(viewModelScope)
            }
        }
    }

    private fun getUpcomingFilms(genreId: Int?) {
        viewModelScope.launch {
            _upcomingFilms.value = if (genreId != null) {
                filmRepository.getUpcomingTvShows().map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmRepository.getUpcomingTvShows().cachedIn(viewModelScope)
            }
        }
    }

    private fun getBackInTheDaysFilms(genreId: Int?, filmType: FilmType) {
        viewModelScope.launch {
            _backInTheDaysFilms.value = if (genreId != null) {
                filmRepository.getBackInTheDaysFilms(filmType).map { results ->
                    results.filter { movie ->
                        movie.genreIds!!.contains(genreId)
                    }
                }.cachedIn(viewModelScope)
            } else {
                filmRepository.getBackInTheDaysFilms(filmType).cachedIn(viewModelScope)
            }
        }
    }
}