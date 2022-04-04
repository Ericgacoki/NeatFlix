package com.ericg.neatflix.data.repository

import com.ericg.neatflix.data.response.GenreResponse
import com.ericg.neatflix.model.APIService
import com.ericg.neatflix.util.Resource
import java.lang.Exception
import javax.inject.Inject

class GenreRepository @Inject constructor(private val api: APIService) {
    suspend fun getMoviesGenre(): Resource<GenreResponse>{
        val response = try {
            api.getMovieGenres()
        } catch (e: Exception){
            return Resource.Error("Unknown error occurred!")
        }
        return Resource.Success(response)
    }
}