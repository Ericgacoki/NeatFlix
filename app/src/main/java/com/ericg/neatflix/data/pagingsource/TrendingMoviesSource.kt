package com.ericg.neatflix.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ericg.neatflix.model.APIService
import com.ericg.neatflix.model.Movie
import retrofit2.HttpException
import java.io.IOException

class TrendingMoviesSource(private val api: APIService) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val trendingMovies = api.getTrendingMovies(page = nextPage)

            LoadResult.Page(
                data = trendingMovies.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (trendingMovies.results.isEmpty()) null else trendingMovies.page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}
