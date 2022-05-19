package com.ericg.neatflix.data.remote

import com.ericg.neatflix.BuildConfig import com.ericg.neatflix.data.remote.response.CastResponse
import com.ericg.neatflix.data.remote.response.GenreResponse
import com.ericg.neatflix.data.remote.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendedMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse

    @GET("discover/movie")
    suspend fun getBackInTheDaysMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en",
        @Query("sort_by") sortBy: String = "release_date.asc"
    ): MoviesResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.MUVIZ_API_KEY
    ): CastResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = BuildConfig.MUVIZ_API_KEY,
        @Query("language") language: String = "en"
    ): GenreResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") searchParams: String,
        @Query("page") page: Int = 0,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("api_key") apiKey: String = BuildConfig.MUVIZ_API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse
}
