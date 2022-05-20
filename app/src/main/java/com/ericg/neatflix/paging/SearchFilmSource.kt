package com.ericg.neatflix.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ericg.neatflix.data.remote.APIService
import com.ericg.neatflix.model.Film
import retrofit2.HttpException
import java.io.IOException

class SearchFilmSource(
    private val api: APIService,
    private val searchParams: String,
    private val includeAdult: Boolean
) : PagingSource<Int, Film>() {
    override fun getRefreshKey(state: PagingState<Int, Film>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        return try {
            val nextPage = params.key ?: 1
            val searchMovies = api.searchMovie(
                page = nextPage,
                searchParams = searchParams,
                includeAdult = includeAdult
            )
            LoadResult.Page(
                data = searchMovies.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (searchMovies.results.isEmpty()) null else searchMovies.page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}