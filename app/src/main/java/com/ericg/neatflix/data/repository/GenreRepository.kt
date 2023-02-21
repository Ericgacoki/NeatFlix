package com.ericg.neatflix.data.repository

import com.ericg.neatflix.data.remote.response.GenreResponse
import com.ericg.neatflix.data.remote.ApiService
import com.ericg.neatflix.util.FilmType
import com.ericg.neatflix.util.Resource
import java.lang.Exception
import javax.inject.Inject

class GenreRepository @Inject constructor(private val api: ApiService) {
    suspend fun getMoviesGenre(filmType: FilmType): Resource<GenreResponse>{
        val response = try {
            if (filmType == FilmType.MOVIE) api.getMovieGenres() else api.getTvShowGenres()
        } catch (e: Exception){
            return Resource.Error("Unknown error occurred!")
        }
        return Resource.Success(response)
    }
}