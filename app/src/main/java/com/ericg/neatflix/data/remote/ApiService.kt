@file:Suppress("KDocUnresolvedReference")

package com.ericg.neatflix.data.remote

import com.ericg.neatflix.BuildConfig
import com.ericg.neatflix.data.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
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

    @GET("discover/movie?")
    suspend fun getBackInTheDaysMovies(
        @Query("page") page: Int = 0,
        @Query("primary_release_date.gte") gteReleaseDate: String = "1940-01-01",
        @Query("primary_release_date.lte") lteReleaseDate: String = "1981-01-01",
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en",
        @Query("sort_by") sortBy: String = "vote_count.desc"
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

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShows(
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

    @GET("discover/tv?")
    suspend fun getBackInTheDaysTvShows(
        @Query("page") page: Int = 0,
        @Query("first_air_date.gte") gteFirstAirDate: String = "1940-01-01",
        @Query("first_air_date.lte") lteFirstAirDate: String = "1981-01-01",
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "vote_count.desc"
    ): FilmResponse

    /** Reviews*/
    @GET("{film_path}/{film_id}/reviews?")
    suspend fun getMovieReviews(
        @Path("film_id") filmId: Int,
        @Path("film_path") filmPath: String,
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en-US"
    ): ReviewsResponse

    /** Watch providers (US only)*/

    @GET("{film_path}/{film_id}/watch/providers?")
    suspend fun getWatchProviders(
        @Path("film_path") filmPath: String,
        @Path("film_id") filmId: Int,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
    ): WatchProviderResponse
}
