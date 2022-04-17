package com.ericg.neatflix.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.layout.ContentScale.Companion.Fit
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ericg.neatflix.R
import com.ericg.neatflix.model.Movie
import com.ericg.neatflix.screens.destinations.MovieDetailsDestination
import com.ericg.neatflix.screens.destinations.ProfileDestination
import com.ericg.neatflix.screens.destinations.SearchScreenDestination
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.ericg.neatflix.ui.theme.ButtonColor
import com.ericg.neatflix.util.Constants.BASE_BACKDROP_IMAGE_URL
import com.ericg.neatflix.util.Constants.BASE_POSTER_IMAGE_URL
import com.ericg.neatflix.viewmodel.DetailsViewModel
import com.ericg.neatflix.viewmodel.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@Destination
@Composable
fun Home(
    navigator: DestinationsNavigator?,
    viewModel: HomeViewModel = hiltViewModel(),
    detailsViewModel: DetailsViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180E36))
    ) {
        ProfileAndSearchBar(navigator!!)
        NestedScroll(navigator = navigator, viewModel, detailsViewModel)
    }
}

@Composable
fun ProfileAndSearchBar(
    navigator: DestinationsNavigator
) {
    Row(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .padding(start = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            contentAlignment = Center
        ) {
            Box(
                modifier = Modifier
                    .size(53.dp)
                    .clip(CircleShape)
                    .background(AppOnPrimaryColor)
            )
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(AppPrimaryColor)
            )
            Image(
                painter = painterResource(id = R.drawable.timothee),
                contentScale = Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable {
                        navigator.navigate(
                            direction = ProfileDestination()
                        ) {
                            launchSingleTop = true
                        }
                    },
                contentDescription = "profile picture"
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.neatflix_logo_large),
                contentScale = Fit,
                alpha = 0.78F,
                modifier = Modifier
                    .padding(bottom = 8.dp, top = 4.dp)
                    .widthIn(max = 110.dp),
                contentDescription = "logo"
            )

            val filmCategories = listOf("Movies", "Tv Shows")
            var selectedFilmCategory by remember { mutableStateOf(filmCategories[0]) }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                filmCategories.forEachIndexed { index, filmType ->
                    Text(
                        text = filmType,
                        fontWeight = if (selectedFilmCategory == filmCategories[index]) FontWeight.Bold else FontWeight.Light,
                        fontSize = if (selectedFilmCategory == filmCategories[index]) 24.sp else 16.sp,
                        color = if (selectedFilmCategory == filmCategories[index])
                            AppOnPrimaryColor else Color.LightGray.copy(alpha = 0.78F),
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp, top = 8.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                selectedFilmCategory = filmCategories[index]
                            }

                    )
                }
            }

            val animOffset = animateDpAsState(
                targetValue = when (filmCategories.indexOf(selectedFilmCategory)) {
                    0 -> (-30).dp
                    else -> 30.dp
                },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy
                )
            )

            Box(
                modifier = Modifier
                    .width(46.dp)
                    .height(2.dp)
                    .offset(x = animOffset.value)
                    .clip(RoundedCornerShape(4.dp))
                    .background(AppOnPrimaryColor)
            ) {
            }
        }

        IconButton(
            onClick = {
                navigator.navigate(
                    direction = SearchScreenDestination()
                ) {
                    launchSingleTop = true
                }
            }
        ) {
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search icon",
                tint = AppOnPrimaryColor
            )
        }
    }
}

@Composable
fun NestedScroll(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel,
    detailsViewModel: DetailsViewModel
) {
    val trendingMovies = viewModel.trendingMoviesState.value.collectAsLazyPagingItems()
    val popularMovies = viewModel.popularMoviesState.value.collectAsLazyPagingItems()
    val topRatedMovies = viewModel.topRatedMoviesState.value.collectAsLazyPagingItems()
    val nowPlayingMovies = viewModel.nowPlayingMoviesState.value.collectAsLazyPagingItems()
    val upcomingMovies = viewModel.upcomingMoviesState.value.collectAsLazyPagingItems()

    val listState: LazyListState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .fillMaxSize()
    ) {
        item {
            Text(
                text = "Genres",
                fontSize = 24.sp,
                color = AppOnPrimaryColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(all = 4.dp)
            )
        }
        item {
            val genres = viewModel.movieGenres

            LazyRow(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                items(count = genres.size) {
                    SelectableGenreChip(
                        genre = genres[it].name,
                        selected = genres[it].name == viewModel.selectedGenre.value
                    ) {
                        if (viewModel.selectedGenre.value != genres[it].name) {
                            viewModel.setSelectedGenre(genre = genres[it])
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Trending",
                fontSize = 24.sp,
                color = AppOnPrimaryColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, top = 8.dp)
            )
        }
        item {
            ScrollableMovieItems(
                detailsViewModel = detailsViewModel,
                landscape = true,
                navigator = navigator,
                pagingItems = trendingMovies,
                onErrorClick = {
                    viewModel.refreshAll()
                }
            )
        }

        item {
            Text(
                text = "Popular",
                fontSize = 24.sp,
                color = AppOnPrimaryColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, top = 6.dp)
            )
        }
        item {
            ScrollableMovieItems(
                navigator = navigator,
                pagingItems = popularMovies,
                onErrorClick = {
                    viewModel.refreshAll()
                }
            )
        }

        item {
            Text(
                text = "Top Rated",
                fontSize = 24.sp,
                color = AppOnPrimaryColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, top = 14.dp, bottom = 8.dp)
            )
        }
        item {
            ScrollableMovieItems(
                navigator = navigator,
                pagingItems = topRatedMovies,
                onErrorClick = {
                    viewModel.refreshAll()
                }
            )
        }

        item {
            Text(
                text = "Now Playing",
                fontSize = 24.sp,
                color = AppOnPrimaryColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, top = 14.dp, bottom = 4.dp)
            )
        }
        item {
            ScrollableMovieItems(
                navigator = navigator,
                pagingItems = nowPlayingMovies,
                onErrorClick = {
                    viewModel.refreshAll()
                }
            )
        }

        item {
            Text(
                text = "Upcoming",
                fontSize = 24.sp,
                color = AppOnPrimaryColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, top = 14.dp, bottom = 4.dp)
            )
        }
        item {
            ScrollableMovieItems(
                navigator = navigator,
                pagingItems = upcomingMovies,
                onErrorClick = {
                    viewModel.refreshAll()
                }
            )
        }
/*
        item {
            Text(
                text = "Watch List",
                fontSize = 24.sp,
                color = AppOnPrimaryColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, top = 14.dp, bottom = 4.dp)
            )
        }
        item {
            ScrollableMovieItems(
                navigator = navigator,
                pagingItems = topRatedMovies, // watchList -> from room db
                onErrorClick = {
                    viewModel.refreshAll()
                }
            )
        }
        item {
            Text(
                text = "Recommended",
                fontSize = 24.sp,
                color = AppOnPrimaryColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, top = 14.dp, bottom = 4.dp)
            )
        }
        item {
            ScrollableMovieItems(
                navigator = navigator,
                pagingItems = topRatedMovies, // recommended -> fetched using a random id from watchlist and/or from the current genre
                onErrorClick = {
                    viewModel.refreshAll()
                }
            )
        }

        item {
            Text(
                text = "Back in the days",
                fontSize = 24.sp,
                color = AppOnPrimaryColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp, top = 14.dp, bottom = 4.dp)
            )
        }
        item {
            ScrollableMovieItems(
                navigator = navigator,
                pagingItems = topRatedMovies, // backInTheDays
                onErrorClick = {
                    viewModel.refreshAll()
                }
            )
        }*/

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MovieItem(
    imageUrl: String,
    title: String,
    modifier: Modifier,
    landscape: Boolean,
    onclick: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(all = 4.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onclick()
            },
        horizontalAlignment = Alignment.Start
    ) {
        CoilImage(
            imageModel = imageUrl,
            shimmerParams = ShimmerParams(
                baseColor = AppPrimaryColor,
                highlightColor = ButtonColor,
                durationMillis = 350,
                dropOff = 0.65F,
                tilt = 20F
            ),
            failure = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_image_failed),
                        tint = Color(0xFFFF6F6F),
                        contentDescription = null
                    )
                }
            },
            previewPlaceholder = R.drawable.dont_look_up,
            contentScale = Crop,
            circularReveal = CircularReveal(duration = 1000),
            modifier = modifier.clip(RoundedCornerShape(8.dp)),
            contentDescription = "Movie item"
        )

        AnimatedVisibility(visible = landscape) {
            Text(
                text = trimTitle(title),
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                color = AppOnPrimaryColor,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
        }
    }
}

private fun trimTitle(text: String) = if (text.length <= 30) text else {
    val textWithEllipsis = text.removeRange(startIndex = 30, endIndex = text.length)
    "$textWithEllipsis..."
}

@Composable
private fun ScrollableMovieItems(
    landscape: Boolean = false,
    detailsViewModel: DetailsViewModel? = null,
    navigator: DestinationsNavigator,
    pagingItems: LazyPagingItems<Movie>,
    onErrorClick: () -> Unit
) {
    Box(
        contentAlignment = Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(if (landscape) 215.dp else 195.dp)
    ) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading ->
                // TODO("Use Lotti File")
                CircularProgressIndicator(
                    modifier = Modifier.width(40.dp)
                )
            is LoadState.NotLoading -> {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(pagingItems) { movie ->
                        val imagePath =
                            if (landscape) "$BASE_BACKDROP_IMAGE_URL/${movie!!.backdropPath}"
                            else "$BASE_POSTER_IMAGE_URL/${movie!!.posterPath}"

                        MovieItem(
                            landscape = landscape,
                            imageUrl = imagePath,
                            title = movie.title,
                            modifier = Modifier
                                .width(if (landscape) 215.dp else 130.dp)
                                .height(if (landscape) 161.25.dp else 195.dp)
                        ) {
                            Timber.e("Clicked MovieItem with id ${movie.id}")
                            detailsViewModel!!.getSimilarMovies(movie.id)
                            navigator.navigate(
                                direction = MovieDetailsDestination(movie)
                            ) {
                                launchSingleTop = true
                            }
                        }
                    }
                }
            }
            is LoadState.Error -> {
                val error = pagingItems.loadState.refresh as LoadState.Error
                val errorMessage = when (error.error) {
                    is HttpException -> "Sorry, Something went wrong!\nTap to retry"
                    is IOException -> "Connection failed. Tap to retry!"
                    else -> "Failed! Tap to retry!"
                }
                Box(contentAlignment = Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(161.25.dp) // maintain the vertical space between two categories
                        .clickable {
                            onErrorClick()
                        }
                ) {
                    Text(
                        text = errorMessage,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = Light,
                        color = Color(0xFFE28B8B),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            else -> {
            }
        }
    }
}

@Composable
fun SelectableGenreChip(
    genre: String,
    selected: Boolean,
    onclick: () -> Unit
) {

    val animateChipBackgroundColor by animateColorAsState(
        targetValue = if (selected) Color(0xFFA0A1C2) else ButtonColor.copy(alpha = 0.5F),
        animationSpec = tween(
            durationMillis = if (selected) 100 else 50,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .clip(CircleShape)
            .background(
                color = animateChipBackgroundColor
            )
            .height(32.dp)
            .widthIn(min = 80.dp)
            /*.border(
                width = 0.5.dp,
                color = Color(0xC69495B1),
                shape = CircleShape
            )*/
            .padding(horizontal = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onclick()
            }
    ) {
        Text(
            text = genre,
            fontWeight = if (selected) FontWeight.Normal else FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Center),
            color = if (selected) Color(0XFF180E36) else Color.White.copy(alpha = 0.80F)
        )
    }
}

@Preview
@Composable
fun HomePrev() {
    Home(null)
}
