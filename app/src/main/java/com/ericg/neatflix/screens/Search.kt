package com.ericg.neatflix.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ericg.neatflix.model.Film
import com.ericg.neatflix.R
import com.ericg.neatflix.screens.destinations.MovieDetailsDestination
import com.ericg.neatflix.sharedComposables.BackButton
import com.ericg.neatflix.sharedComposables.SearchBar
import com.ericg.neatflix.sharedComposables.SearchResultItem
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.ericg.neatflix.util.Constants.BASE_POSTER_IMAGE_URL
import com.ericg.neatflix.util.FilmType
import com.ericg.neatflix.viewmodel.HomeViewModel
import com.ericg.neatflix.viewmodel.SearchViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    searchViewModel: SearchViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val searchResult = searchViewModel.multiSearchState.value.collectAsLazyPagingItems()
    val includeAdult =
        searchViewModel.includeAdult.value.collectAsState(initial = true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppPrimaryColor)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 16.dp)
                .fillMaxWidth(fraction = 0.60F)
        ) {
            val focusManager = LocalFocusManager.current
            BackButton {
                focusManager.clearFocus()
                navigator.navigateUp()
            }

            Text(
                text = "Search",
                modifier = Modifier.padding(start = 50.dp),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = AppOnPrimaryColor
            )
        }

        SearchBar(
            autoFocus = true,
            onSearch = {
                searchViewModel.searchRemoteMovie(includeAdult.value ?: true)
            })

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            when (searchResult.loadState.refresh) {
                is LoadState.NotLoading -> {
                    items(searchResult) { film ->
                        val focus = LocalFocusManager.current
                        SearchResultItem(
                            title = film!!.title,
                            mediaType = film.mediaType,
                            posterImage = "$BASE_POSTER_IMAGE_URL/${film.posterPath}",
                            genres = homeViewModel.filmGenres.filter { genre ->
                                return@filter if (film.genreIds.isNullOrEmpty()) false else
                                    film.genreIds.contains(genre.id)
                            },
                            rating = (film.voteAverage ?: 0) as Double,
                            releaseYear = film.releaseDate,
                            onClick = {
                                val navFilm = Film(
                                    adult = film.adult ?: false,
                                    backdropPath = film.backdropPath,
                                    posterPath = film.posterPath,
                                    genreIds = film.genreIds,
                                    genres = film.genres,
                                    mediaType = film.mediaType,
                                    id = film.id ?: 0,
                                    imdbId = film.imdbId,
                                    originalLanguage = film.originalLanguage ?: "",
                                    overview = film.overview ?: "",
                                    popularity = film.popularity ?: 0F.toDouble(),
                                    releaseDate = film.releaseDate ?: "",
                                    runtime = film.runtime,
                                    title = film.title ?: "",
                                    video = film.video ?: false,
                                    voteAverage = film.voteAverage ?: 0F.toDouble(),
                                    voteCount = film.voteCount ?: 0
                                )
                                focus.clearFocus()
                                navigator.navigate(
                                    direction = MovieDetailsDestination(
                                        navFilm,
                                        if (navFilm.mediaType == "tv") FilmType.TVSHOW else FilmType.MOVIE
                                    )
                                ) {
                                    launchSingleTop = true
                                }
                            })
                    }
                    if (searchResult.itemCount == 0) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 60.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.no_match_found),
                                    contentDescription = null
                                )
                            }

                        }
                    }
                }

                is LoadState.Loading -> item {
                    if (searchViewModel.searchParam.value.isNotEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                else -> item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.no_match_found),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}
