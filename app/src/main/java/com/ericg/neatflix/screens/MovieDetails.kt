package com.ericg.neatflix.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ericg.neatflix.R
import com.ericg.neatflix.model.CastDemo
import com.ericg.neatflix.model.Movie
import com.ericg.neatflix.sharedComposables.MovieGenreChip
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.ericg.neatflix.ui.theme.ButtonColor
import com.ericg.neatflix.util.Constants.BASE_BACKDROP_IMAGE_URL
import com.ericg.neatflix.util.Constants.BASE_POSTER_IMAGE_URL
import com.ericg.neatflix.viewmodel.DetailsViewModel
import com.ericg.neatflix.viewmodel.HomeViewModel
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Destination
@Composable
fun MovieDetails(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel(),
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    currentMovie: Movie
) {
    var movie by remember {
        mutableStateOf(currentMovie)
    }

    LaunchedEffect(key1 = movie) {
        detailsViewModel.getSimilarMovies(movieId = movie.id)
    }
    val similarMovies = detailsViewModel.similarMovies.value.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180E36))
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33F)
        ) {
            val (
                backdropImage,
                backButton,
                movieTitleBox,
                moviePosterImage,
                translucentBr
            ) = createRefs()

            CoilImage(
                imageModel = "$BASE_BACKDROP_IMAGE_URL/${movie.backdropPath}",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                    .fillMaxHeight()
                    .constrainAs(backdropImage) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
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
                shimmerParams = ShimmerParams(
                    baseColor = AppPrimaryColor,
                    highlightColor = ButtonColor,
                    durationMillis = 350,
                    dropOff = 0.65F,
                    tilt = 20F
                ),
                contentScale = Crop,
                contentDescription = "Header backdrop image",
            )

            ConstraintLayout(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(ButtonColor.copy(alpha = 0.78F))
                    .constrainAs(backButton) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 10.dp)
                    }
            ) {
                val (icon) = createRefs()
                IconButton(onClick = {
                    navigator.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "back button",
                        tint = AppOnPrimaryColor,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                            .constrainAs(icon) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color(0XFF180E36).copy(alpha = 0.5F),
                                Color(0XFF180E36)
                            ),
                            startY = 0.1F
                        )
                    )
                    .constrainAs(translucentBr) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(backdropImage.bottom)
                    }
            )

            Column(
                modifier = Modifier.constrainAs(movieTitleBox) {
                    start.linkTo(moviePosterImage.end, margin = 12.dp)
                    end.linkTo(parent.end, margin = 12.dp)
                    bottom.linkTo(moviePosterImage.bottom, margin = 10.dp)
                },
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = movie.title,
                    modifier = Modifier
                        .padding(start = 4.dp, bottom = 8.dp)
                        .fillMaxWidth(0.5F),
                    maxLines = 2,
                    fontSize = 18.sp,
                    fontWeight = Bold,
                    color = Color.White.copy(alpha = 0.78F)
                )

                Text(
                    text = movie.releaseDate,
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
                    fontSize = 15.sp,
                    fontWeight = Light,
                    color = Color.White.copy(alpha = 0.56F)
                )

                RatingBar(
                    value = (movie.voteAverage / 2).toFloat(),
                    modifier = Modifier.padding(horizontal = 6.dp),
                    config = RatingBarConfig()
                        .style(RatingBarStyle.Normal)
                        .isIndicator(true)
                        .activeColor(Color(0XFFC9F964))
                        .hideInactiveStars(false)
                        .inactiveColor(Color.LightGray.copy(alpha = 0.3F))
                        .stepSize(StepSize.HALF)
                        .numStars(5)
                        .size(16.dp)
                        .padding(4.dp),
                    onValueChange = {},
                    onRatingChanged = {}
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 4.dp, bottom = 8.dp)
                        .fillMaxWidth(0.42F),
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .border(
                                1.dp,
                                color = if (movie.adult) Color(0xFFFF6F6F) else Color.White.copy(
                                    alpha = 0.78F
                                )
                            )
                            .background(if (movie.adult) Color(0xFFFF6F6F).copy(alpha = 0.14F) else Color.Transparent)
                            .padding(4.dp)
                    ) {
                        val color: Color
                        Text(
                            text = if (movie.adult) {
                                color = Color(0xFFFF6F6F)
                                "18+"
                            } else {
                                color = Color.White.copy(alpha = 0.56F)
                                "PG"
                            },
                            fontSize = 14.sp,
                            fontWeight = Normal,
                            color = color
                        )
                    }

                    var addedToList by remember { mutableStateOf(false) }
                    IconButton(onClick = {
                        addedToList = !addedToList
                    }) {
                        Icon(
                            painter = painterResource(
                                id = if (addedToList) R.drawable.ic_added_to_list
                                else R.drawable.ic_add_to_list
                            ),
                            tint = AppOnPrimaryColor,
                            contentDescription = "add to watch list icon"
                        )
                    }
                }
            }

            CoilImage(
                imageModel = "$BASE_POSTER_IMAGE_URL/${movie.posterPath}",
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .width(115.dp)
                    .height(172.5.dp)
                    .constrainAs(moviePosterImage) {
                        top.linkTo(backdropImage.bottom)
                        bottom.linkTo(backdropImage.bottom)
                        start.linkTo(parent.start)
                    }, failure = {
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
                shimmerParams = ShimmerParams(
                    baseColor = AppPrimaryColor,
                    highlightColor = ButtonColor,
                    durationMillis = 350,
                    dropOff = 0.65F,
                    tilt = 20F
                ),
                previewPlaceholder = R.drawable.dont_look_up,
                contentScale = Crop,
                circularReveal = CircularReveal(duration = 1000),
                contentDescription = "movie poster"
            )
        }

        LazyRow(
            modifier = Modifier
                .padding(top = (96).dp, bottom = 4.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth()
        ) {
            val movieGenres = homeViewModel.movieGenres.filter { genre ->
                movie.genreIds!!.contains(genre.id)
            }
            movieGenres.forEach { genre ->
                item {
                    MovieGenreChip(
                        background = ButtonColor,
                        textColor = AppOnPrimaryColor,
                        genre = genre.name
                    )
                }
            }
        }

        ExpandableText(
            text = movie.overview,
            modifier = Modifier
                .padding(top = 3.dp, bottom = 4.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth()
        )

        LazyColumn(
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Text(
                    text = "Cast",
                    fontWeight = Bold,
                    fontSize = 18.sp,
                    color = AppOnPrimaryColor,
                    modifier = Modifier.padding(start = 4.dp, top = 6.dp, bottom = 4.dp)
                )
            }
            item {
                LazyRow(modifier = Modifier.padding(4.dp)) {
                    val demoCastList = mutableListOf<CastDemo>()
                    (1..10).map {
                        demoCastList.add(CastDemo(R.drawable.mountain))
                    }

                    demoCastList.forEach {
                        item {
                            CastCrew(castMember = it)
                        }
                    }
                }
            }
            item {
                Text(
                    text = "Similar",
                    fontWeight = Bold,
                    fontSize = 18.sp,
                    color = AppOnPrimaryColor,
                    modifier = Modifier.padding(start = 4.dp, top = 6.dp, bottom = 4.dp)
                )
            }

            item {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(similarMovies) { thisMovie ->
                        CoilImage(
                            imageModel = "${BASE_POSTER_IMAGE_URL}/${thisMovie!!.posterPath}",
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
                            modifier = Modifier
                                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .size(130.dp, 195.dp)
                                .clickable {
                                    movie = thisMovie
                                },
                            contentDescription = "Movie item"
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CastCrew(castMember: CastDemo) {
    Column(
        modifier = Modifier.padding(end = 8.dp, top = 4.dp, bottom = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(70.dp),
            imageModel = castMember.image,
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.background,
                highlightColor = AppOnPrimaryColor,
                durationMillis = 350,
                dropOff = 0.65F,
                tilt = 20F
            ),
            previewPlaceholder = R.drawable.timothee,
            contentScale = Crop,
            circularReveal = CircularReveal(duration = 1000),
            contentDescription = "cast image"
        )
        Text(
            text = castName("Timothee"),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = AppOnPrimaryColor.copy(alpha = 0.45F),
            fontSize = 14.sp,
        )
    }
}

fun castName(name: String): String {
    return if (name.length <= 8) name else {
        name.removeRange(6..name.lastIndex) + "..."
    }
}

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 2,
) {
    var cutText by remember(text) { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }

    // getting raw values for smart cast
    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value

    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = minimizedMaxLines - 1
        if (!expanded && textLayoutResult != null && seeMoreSize != null &&
            lastLineIndex + 1 == textLayoutResult.lineCount &&
            textLayoutResult.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult.size.width - seeMoreSize.width
            )
            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
        }
    }

    Box(modifier) {
        Text(
            color = AppOnPrimaryColor,
            text = cutText ?: text,
            modifier = Modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    if (expanded) {
                        expanded = false
                    }
                },
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResultState.value = it },
        )

        if (!expanded) {
            val density = LocalDensity.current
            Text(
                color = Color(0x2DFF978C).copy(alpha = 0.78F),
                text = "... See more",
                fontWeight = Bold,
                fontSize = 14.sp,
                onTextLayout = { seeMoreSizeState.value = it.size },
                modifier = Modifier
                    .then(
                        if (seeMoreOffset != null)
                            Modifier.offset(
                                x = with(density) { seeMoreOffset.x.toDp() },
                                y = with(density) { seeMoreOffset.y.toDp() },
                            )
                        else Modifier
                    )
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        expanded = true
                        cutText = null
                    }
                    .alpha(if (seeMoreOffset != null) 1f else 0f)
                    .verticalScroll(
                        enabled = true,
                        state = rememberScrollState()
                    )
            )
        }
    }
}
