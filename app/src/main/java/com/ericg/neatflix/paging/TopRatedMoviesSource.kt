package com.ericg.neatflix.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ericg.neatflix.model.APIService
import com.ericg.neatflix.model.Movie
import retrofit2.HttpException
import java.io.IOException

class TopRatedMoviesSource(private val api: APIService) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val topRatedMovies = api.getTopRatedMovies(page = nextPage)

            LoadResult.Page(
                data = topRatedMovies.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (topRatedMovies.results.isEmpty()) null else topRatedMovies.page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}
