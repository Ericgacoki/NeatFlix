package com.ericg.neatflix.model

import android.content.Context
import com.ericg.neatflix.BuildConfig
import com.ericg.neatflix.data.response.MoviesResponse
import com.ericg.neatflix.util.Constants.API_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface APIService {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.NEATFLIX_API_KEY,
        @Query("language") language: String = "en"
    ): MoviesResponse
}
