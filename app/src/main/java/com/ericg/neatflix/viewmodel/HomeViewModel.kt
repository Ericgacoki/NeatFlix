package com.ericg.neatflix.viewmodel

import androidx.lifecycle.ViewModel
import com.ericg.neatflix.data.MovieGenre as Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val title: String) : ViewModel() {
    fun getMovieTitle(): String = title

    fun getMovieGenres(
        movieId: Int = 0
    ): List<Genre>{
        return listOf(
            Genre(movieId,"Sci-fi"),
            Genre(movieId,"Erotic \uD83C\uDF51"),
            Genre(movieId,"Drama"),
            Genre(movieId,"Fantasy"),
            Genre(movieId,"Adventure"))
    }
}