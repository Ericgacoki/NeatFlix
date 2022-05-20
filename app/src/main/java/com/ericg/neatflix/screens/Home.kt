package com.ericg.neatflix.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ericg.neatflix.R
import com.ericg.neatflix.model.Film
import com.ericg.neatflix.screens.destinations.MovieDetailsDestination
import com.ericg.neatflix.screens.destinations.ProfileDestination
import com.ericg.neatflix.screens.destinations.SearchScreenDestination
import com.ericg.neatflix.sharedComposables.LoopReverseLottieLoader
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.ericg.neatflix.ui.theme.ButtonColor
import com.ericg.neatflix.util.Constants.BASE_BACKDROP_IMAGE_URL
import com.ericg.neatflix.util.Constants.BASE_POSTER_IMAGE_URL
import com.ericg.neatflix.util.FilmType
import com.ericg.neatflix.util.collectAsStateLifecycleAware
import com.ericg.neatflix.viewmodel.HomeViewModel
import com.ericg.neatflix.viewmodel.WatchListViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import retrofit2.HttpException
import java.io.IOException

@Destination
@Composable
fun Home(
    navigator: DestinationsNavigator?,
    homeViewModel: HomeViewModel = hiltViewModel(),
    watchListViewModel: WatchListViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180E36))
    ) {
        ProfileAndSearchBar(navigator!!, homeViewModel)
        NestedScroll(navigator = navigator, homeViewModel, watchListViewModel)
    }
}

@Composable
fun ProfileAndSearchBar(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel
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
                // .background(AppOnPrimaryColor)
            )
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(AppPrimaryColor)
            )
            IconButton(onClick = {
                navigator.navigate(
                    direction = ProfileDestination()
                ) {
                    launchSingleTop = true
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person_profile),
                    tint = AppOnPrimaryColor,
                    modifier = Modifier.size(32.dp),
                    contentDescription = "profile picture"
                )
            }
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

            val filmTypes = listOf(FilmType.MOVIE, FilmType.TVSHOW)
            val selectedFilmType = homeViewModel.selectedFilmType.value

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                filmTypes.forEachIndexed { index, filmType ->
                    Text(
                        text = if (filmType == FilmType.MOVIE) "Movies" else "Tv Shows",
                        fontWeight = if (selectedFilmType == filmTypes[index]) FontWeight.Bold else Light,
                        fontSize = if (selectedFilmType == filmTypes[index]) 24.sp else 16.sp,
                        color = if (selectedFilmType == filmTypes[index])
                            AppOnPrimaryColor else Color.LightGray.copy(alpha = 0.78F),
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp, top = 8.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (homeViewModel.selectedFilmType.value != filmTypes[index]) {
                                    homeViewModel.selectedFilmType.value = filmTypes[index]
                                    homeViewModel.getFilmGenre()
                                    homeViewModel.refreshAll(null)
                                }
                            }
                    )
                }
            }

            val animOffset = animateDpAsState(
                targetValue = when (filmTypes.indexOf(selectedFilmType)) {
                    0 -> (-35).dp
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
            )
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
                modifier = Modifier.size(28.dp),
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
    homeViewModel: HomeViewModel,
    watchListViewModel: WatchListViewModel
) {
    val trendingFilms = homeViewModel.trendingMoviesState.value.collectAsLazyPagingItems()
    val popularFilms = homeViewModel.popularFilmsState.value.collectAsLazyPagingItems()
    val topRatedFilms = homeViewModel.topRatedFilmState.value.collectAsLazyPagingItems()
    val nowPlayingFilms = homeViewModel.nowPlayingMoviesState.value.collectAsLazyPagingItems()
    val upcomingMovies = homeViewModel.upcomingMoviesState.value.collectAsLazyPagingItems()
    val backInTheDays = homeViewModel.backInTheDaysMoviesState.value.collectAsLazyPagingItems()
    val recommendedFilms = homeViewModel.recommendedMovies.value.collectAsLazyPagingItems()
    val myWatchList =
        watchListViewModel.watchList.value.collectAsStateLifecycleAware(initial = emptyList())

    LaunchedEffect(key1 = myWatchList.value.size) {
        if (myWatchList.value.isNotEmpty()) {
            homeViewModel.randomMovieId =
                myWatchList.value[(0..myWatchList.value.lastIndex).random()].mediaId
            if (recommendedFilms.itemCount == 0) {
                homeViewModel.getRecommendedFilms(movieId = homeViewModel.randomMovieId!!)
            }
        }
    }

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
            val genres = homeViewModel.filmGenres

            LazyRow(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                items(count = genres.size) {
                    SelectableGenreChip(
                        genre = genres[it].name,
                        selected = genres[it].name == homeViewModel.selectedGenre.value.name,
                        onclick = {
                            if (homeViewModel.selectedGenre.value.name != genres[it].name) {
                                homeViewModel.selectedGenre.value = genres[it]
                                homeViewModel.filterBySetSelectedGenre(genre = genres[it])
                            }
                        }
                    )
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
                landscape = true,
                navigator = navigator,
                pagingItems = trendingFilms,
                onErrorClick = {
                    homeViewModel.refreshAll()
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
                pagingItems = popularFilms,
                onErrorClick = {
                    homeViewModel.refreshAll()
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
                pagingItems = topRatedFilms,
                onErrorClick = {
                    homeViewModel.refreshAll()
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
                pagingItems = nowPlayingFilms,
                onErrorClick = {
                    homeViewModel.refreshAll()
                }
            )
        }

        if (homeViewModel.selectedFilmType.value == FilmType.MOVIE) {
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
                        homeViewModel.refreshAll()
                    }
                )
            }
        }

        if (recommendedFilms.itemCount != 0) {
            item {
                ShowAboutCategory(
                    name = "For You",
                    description = "Recommendation based on your watchlist"
                )
            }

            item {
                ScrollableMovieItems(
                    navigator = navigator,
                    pagingItems = recommendedFilms,
                    onErrorClick = {
                        homeViewModel.refreshAll()
                        if (myWatchList.value.isNotEmpty()) {
                            val randomMovieId =
                                myWatchList.value[(0..myWatchList.value.lastIndex).random()].mediaId
                            homeViewModel.getRecommendedFilms(movieId = randomMovieId)
                        }
                    }
                )
            }
        }

        if (backInTheDays.itemCount != 0) {
            item {
                ShowAboutCategory(
                    name = "Back in the Days",
                    description = "A list of very old films \uD83E\uDD2D"
                )
            }
            item {
                ScrollableMovieItems(
                    navigator = navigator,
                    pagingItems = backInTheDays,
                    onErrorClick = {
                        homeViewModel.refreshAll()
                    }
                )
            }
        }

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
                    contentAlignment = Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_image_failed),
                        tint = Color(0xFFFF6F6F),
                        contentDescription = null
                    )
                }
            },
            previewPlaceholder = R.drawable.popcorn,
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
                fontWeight = Normal,
                textAlign = TextAlign.Start
            )
        }
    }
}

private fun trimTitle(text: String) = if (text.length <= 26) text else {
    val textWithEllipsis = text.removeRange(startIndex = 26, endIndex = text.length)
    "$textWithEllipsis..."
}

@Composable
private fun ScrollableMovieItems(
    landscape: Boolean = false,
    showStickyBadge: Boolean = false,
    navigator: DestinationsNavigator,
    pagingItems: LazyPagingItems<Film>,
    onErrorClick: () -> Unit
) {
    Box(
        contentAlignment = Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(if (!landscape) 215.dp else 195.dp)
    ) {
        when (pagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                LoopReverseLottieLoader(lottieFile = R.raw.loader)
            }
            is LoadState.NotLoading -> {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(pagingItems) { film ->
                        val imagePath =
                            if (landscape) "$BASE_BACKDROP_IMAGE_URL/${film!!.backdropPath}"
                            else "$BASE_POSTER_IMAGE_URL/${film!!.posterPath}"

                        MovieItem(
                            landscape = landscape,
                            imageUrl = imagePath,
                            title = film.title,
                            modifier = Modifier
                                .width(if (landscape) 215.dp else 130.dp)
                                .height(if (landscape) 161.25.dp else 195.dp)
                        ) {
                            navigator.navigate(
                                direction = MovieDetailsDestination(film)
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
            fontWeight = if (selected) Normal else Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Center),
            color = if (selected) Color(0XFF180E36) else Color.White.copy(alpha = 0.80F)
        )
    }
}

@Composable
fun ShowAboutCategory(name: String, description: String) {
    var showAboutThisCategory by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = name,
            fontSize = 24.sp,
            color = AppOnPrimaryColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                start = 4.dp, top = 14.dp,
                end = 8.dp, bottom = 4.dp
            )
        )
        IconButton(
            modifier = Modifier.padding(top = 14.dp, bottom = 4.dp),
            onClick = { showAboutThisCategory = showAboutThisCategory.not() }) {
            Icon(
                imageVector = if (showAboutThisCategory) Icons.Filled.KeyboardArrowUp else Icons.Filled.Info,
                tint = AppOnPrimaryColor,
                contentDescription = "Info Icon"
            )
        }
    }

    AnimatedVisibility(visible = showAboutThisCategory) {
        Box(
            contentAlignment = Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .border(
                    width = 1.dp, color = ButtonColor,
                    shape = RoundedCornerShape(4.dp)
                )
                .background(ButtonColor.copy(alpha = 0.25F))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = description,
                    color = AppOnPrimaryColor
                )
            }
        }
    }
}
