package com.ericg.neatflix.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.ericg.neatflix.R
import com.ericg.neatflix.screens.destinations.MovieDetailsDestination
import com.ericg.neatflix.sharedComposables.BackButton
import com.ericg.neatflix.sharedComposables.SearchBar
import com.ericg.neatflix.sharedComposables.SearchResultItem
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.ericg.neatflix.util.Constants.BASE_POSTER_IMAGE_URL
import com.ericg.neatflix.viewmodel.HomeViewModel
import com.ericg.neatflix.viewmodel.SearchViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val searchResult = viewModel.searchMoviesState.value.collectAsLazyPagingItems()

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
                viewModel.searchMovie()
            })

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            when (searchResult.loadState.refresh) {
                is LoadState.NotLoading -> {
                    items(searchResult) { movie ->
                        val focus = LocalFocusManager.current

                        SearchResultItem(
                            title = movie!!.title,
                            posterImage = "$BASE_POSTER_IMAGE_URL/${movie.posterPath}",
                            genres = homeViewModel.movieGenres.filter { genre ->
                                movie.genreIds!!.contains(genre.id)
                            },
                            rating = movie.voteAverage,
                            releaseYear = movie.releaseDate
                        ) {
                            focus.clearFocus()
                            navigator.navigate(
                                direction = MovieDetailsDestination(movie)

                            ) {
                                launchSingleTop = true
                            }
                        }
                    }
                    if (searchResult.itemCount == 0) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                else -> item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
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
