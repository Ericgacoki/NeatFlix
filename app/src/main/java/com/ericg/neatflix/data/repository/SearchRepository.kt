package com.ericg.neatflix.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ericg.neatflix.paging.SearchMovieSource
import com.ericg.neatflix.data.remote.APIService
import com.ericg.neatflix.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: APIService
) {
    fun multiSearch(searchParams: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                SearchMovieSource(api = api, searchParams = searchParams)
            }
        ).flow
    }
}