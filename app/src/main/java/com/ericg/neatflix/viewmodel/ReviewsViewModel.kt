package com.ericg.neatflix.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ericg.neatflix.data.remote.response.Review
import com.ericg.neatflix.data.repository.ReviewsRepository
import com.ericg.neatflix.util.FilmType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(private val repo: ReviewsRepository) : ViewModel() {

    private val _filmReviews = mutableStateOf<Flow<PagingData<Review>>>(emptyFlow())
    val filmReviews: State<Flow<PagingData<Review>>> = _filmReviews

    fun getFilmReview(filmId: Int, filmType: FilmType) {
        viewModelScope.launch {
            _filmReviews.value = repo.getFilmReviews(filmId = filmId, filmType).cachedIn(viewModelScope)
        }
    }
}
