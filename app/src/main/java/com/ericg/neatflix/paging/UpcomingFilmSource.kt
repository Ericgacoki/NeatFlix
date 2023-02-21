package com.ericg.neatflix.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ericg.neatflix.data.remote.ApiService
import com.ericg.neatflix.model.Film
import retrofit2.HttpException
import java.io.IOException

class UpcomingFilmSource(private val api: ApiService) :
    PagingSource<Int, Film>() {
    override fun getRefreshKey(state: PagingState<Int, Film>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        return try {
            val nextPage = params.key ?: 1
            val upcomingMovies = api.getUpcomingMovies(page = nextPage)


            LoadResult.Page(
                data = upcomingMovies.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (upcomingMovies.results.isEmpty()) null else upcomingMovies.page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}