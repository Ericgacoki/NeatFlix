package com.ericg.neatflix.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(val title: String) : ViewModel() {
    fun getMovieTitle(): String = title
}
