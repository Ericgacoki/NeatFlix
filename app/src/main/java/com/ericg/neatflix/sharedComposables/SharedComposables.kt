package com.ericg.neatflix.sharedComposables

import androidx.annotation.RawRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.*
import com.ericg.neatflix.R
import com.ericg.neatflix.data.MovieGenre
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.ericg.neatflix.ui.theme.ButtonColor
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import timber.log.Timber

@Composable
fun NextButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(ButtonColor)
        ) {
            ConstraintLayout {
                val (icon) = createRefs()
                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = null,
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
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onClick() }
                )
            }
        }
    }
}

// Fixme: A very bad way to expose a state... but sina otherwise!
var globalExposedSearchParam: String? = null

@Composable
fun SearchBar(
    autoFocus: Boolean,
    onSearch: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
            .clip(CircleShape)
            .background(ButtonColor)
            .fillMaxWidth()
            .height(54.dp)
    ) {
        var searchInput: String by remember { mutableStateOf("") }
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        TextField(
            value = searchInput,
            onValueChange = { newValue ->
                searchInput = if (newValue.trim().isNotEmpty()) newValue else ""
                globalExposedSearchParam = searchInput
                onSearch()
            },
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester = focusRequester),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Search...",
                    color = AppOnPrimaryColor.copy(alpha = 0.8F)
                )
            },
            colors = textFieldColors(
                textColor = Color.White.copy(alpha = 0.78F),
                backgroundColor = Color.Transparent,
                disabledTextColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ), keyboardOptions = KeyboardOptions(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    onSearch()
                }
            ),
            trailingIcon = {
                LaunchedEffect(Unit) {
                    if (autoFocus) {
                        focusRequester.requestFocus()
                    }
                }
                Row {
                    AnimatedVisibility(visible = searchInput.trim().isNotEmpty()) {
                        IconButton(onClick = {
                            focusManager.clearFocus()
                            searchInput = ""
                            globalExposedSearchParam = null
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                tint = AppOnPrimaryColor,
                                contentDescription = null
                            )
                        }
                    }

                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onSearch()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            tint = AppOnPrimaryColor,
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(ButtonColor)
    ) {
        val (icon) = createRefs()
        IconButton(onClick = { onClick() }) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
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
}

@Composable
fun SearchResultItem(
    imdbId: String,
    title: String,
    posterImage: Int,
    genres: List<MovieGenre>,
    rating: Float,
    releaseYear: String,
    showFavorite: Boolean = false,
    onRemoveFavorite: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 12.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ButtonColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                imageModel = posterImage,
                circularReveal = CircularReveal(duration = 1000),
                previewPlaceholder = R.drawable.manifest,
                shimmerParams = ShimmerParams(
                    baseColor = AppPrimaryColor,
                    highlightColor = ButtonColor,
                    durationMillis = 350,
                    dropOff = 0.65F,
                    tilt = 20F
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(86.67.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = Ellipsis,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    color = AppOnPrimaryColor
                )

                Text(
                    text = releaseYear,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = Color.LightGray
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RatingBar(
                        value = rating,
                        modifier = Modifier.padding(end = 6.dp),
                        config = RatingBarConfig()
                            .style(RatingBarStyle.Normal)
                            .isIndicator(true)
                            .activeColor(Color(0XFFF5BD1F))
                            .hideInactiveStars(false)
                            .inactiveColor(Color.LightGray.copy(alpha = 0.78F))
                            .stepSize(StepSize.HALF)
                            .numStars(5)
                            .size(16.dp)
                            .padding(4.dp),
                        onValueChange = {},
                        onRatingChanged = {}
                    )

                    AnimatedVisibility(visible = showFavorite) {
                        IconButton(onClick = {
                            onRemoveFavorite()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                tint = AppOnPrimaryColor,
                                contentDescription = "fav icon"
                            )
                        }
                    }
                }

                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    genres.forEach {
                        item {
                            MovieGenreChip(genre = it.name)
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun MovieGenreChip(
    genre: String,
    background: Color = Color(0XFFF5BD1F).copy(alpha = 0.20F),
    textColor: Color = Color(0XFFF5BD1F)
) {
    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .widthIn(min = 80.dp)
            .clip(CircleShape)
            .background(background)
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = genre,
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontWeight = FontWeight.Thin
        )
    }
}

@Composable
fun LottieLoader(
    modifier: Modifier,
    @RawRes lottieFile: Int
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(lottieFile),
        onRetry = { failCount, exception ->
            Timber.e("Failed ${failCount}X with exception. Reason: ${exception.localizedMessage}")
            // stop retrying
            false
        }
    )
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier
    )
}

@Composable
fun LoopReverseLottieLoader(
    modifier: Modifier = Modifier,
    @RawRes lottieFile: Int,
    alignment: Alignment = Alignment.Center,
    enableMergePaths: Boolean = true,
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(lottieFile))
    val shouldReverse = remember { mutableStateOf(false) }
    val anim = rememberLottieAnimatable()
    if (shouldReverse.value.not())
        LaunchedEffect(composition) {
            anim.animate(composition = composition, speed = 1f)
            shouldReverse.value = true
        }
    if (shouldReverse.value) {
        LaunchedEffect(composition) {
            anim.animate(composition = composition, speed = -1f)
            shouldReverse.value = false
        }
    }

    LottieAnimation(
        composition,
        anim.value,
        modifier = modifier,
        enableMergePaths = remember { enableMergePaths },
        alignment = alignment
    )
}