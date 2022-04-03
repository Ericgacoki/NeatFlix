package com.ericg.neatflix.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ericg.neatflix.model.APIService
import com.ericg.neatflix.model.Movie
import com.ericg.neatflix.data.pagingsource.TrendingMoviesSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: APIService) {
    fun getTrendingMovies(): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 30),
            pagingSourceFactory = {
                TrendingMoviesSource(api = api)
            }
        ).flow
    }
}