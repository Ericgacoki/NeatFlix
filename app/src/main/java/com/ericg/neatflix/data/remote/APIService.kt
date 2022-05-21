@file:Suppress("KDocUnresolvedReference")

package com.ericg.neatflix.data.remote

import com.ericg.neatflix.BuildConfig import com.ericg.neatflix.data.remote.response.CastResponse
import com.ericg.neatflix.data.remote.response.GenreResponse
import com.ericg.neatflix.data.remote.response.FilmResponse
import com.ericg.neatflix.data.remote.response.MultiSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    /** **Movies** */
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): FilmResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): FilmResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): FilmResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): FilmResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): FilmResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendedMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): FilmResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") filmId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): FilmResponse

    @GET("discover/movie")
    suspend fun getBackInTheDaysMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en",
        @Query("sort_by") sortBy: String = "release_date.asc"
    ): FilmResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") filmId: Int,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY
    ): CastResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): GenreResponse

    @GET("search/multi")
    suspend fun multiSearch(
        @Query("query") searchParams: String,
        @Query("page") page: Int = 0,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): MultiSearchResponse
    /** **Tv Shows**
     *
     * [Note]: **MovieResponse** and **TvShowResponse** attributes are combined based
     * on the fact that you can extract them at runtime by fetching what you need!*/
    @GET("genre/tv/list")
    suspend fun getTvShowGenres(
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en-US"
    ): GenreResponse

    @GET("tv/{tv_id}/credits")
    suspend fun getTvShowCast(
        @Path("tv_id") filmId: Int,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY
    ): CastResponse

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("tv_id") filmId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en-US"
    ): FilmResponse

    @GET("trending/tv/day")
    suspend fun getTrendingTvSeries(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en-US"
    ): FilmResponse

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en-US"
    ): FilmResponse


    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en-US"
    ): FilmResponse

    @GET("tv/on_the_air") // had to use the closest endpoint to /now_playing
    suspend fun getNowPlayingTvShows(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en-US"
    ): FilmResponse

    @GET("tv/{tv_id}/recommendations")
    suspend fun getRecommendedTvShows(
        @Path("tv_id") filmId: Int,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en-US"
    ): FilmResponse

    @GET("discover/tv")
    suspend fun getBackInTheDaysTvShows(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "release_date.asc"
    ): FilmResponse
}
