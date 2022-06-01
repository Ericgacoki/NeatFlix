package com.ericg.neatflix.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ericg.neatflix.data.remote.ApiService
import com.ericg.neatflix.data.remote.response.Review
import com.ericg.neatflix.paging.ReviewsSource
import com.ericg.neatflix.util.FilmType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewsRepository @Inject constructor(private val api: ApiService) {

    fun getFilmReviews(filmId: Int, filmType: FilmType): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                ReviewsSource(api = api, filmId = filmId, filmType = filmType)
            }
        ).flow
    }
}
