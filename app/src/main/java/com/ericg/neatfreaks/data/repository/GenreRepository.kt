package com.ericg.neatfreaks.data.repository

import com.ericg.neatfreaks.data.remote.response.GenreResponse
import com.ericg.neatfreaks.data.remote.ApiService
import com.ericg.neatfreaks.util.FilmType
import com.ericg.neatfreaks.util.Resource
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