package com.ericg.neatflix.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ericg.neatflix.data.pagingsource.*
import com.ericg.neatflix.model.APIService
import com.ericg.neatflix.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: APIService) {
    fun getTrendingMovies(): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20), // by default TMDB API 1 page holds 20 items
            pagingSourceFactory = {
                TrendingMoviesSource(api = api)
            }
        ).flow
    }
    fun getPopularMovies(): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                PopularMoviesSource(api = api)
            }
        ).flow
    }

    fun getTopRatedMovies(): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                TopRatedMoviesSource(api = api)
            }
        ).flow
    }

    fun getNowPlayingMovies(): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                NowPlayingMovieSource(api = api)
            }
        ).flow
    }

    fun getUpcomingMovies(): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                UpcomingMovieSource(api = api)
            }
        ).flow
    }

}