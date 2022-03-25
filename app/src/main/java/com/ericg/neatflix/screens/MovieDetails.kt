package com.ericg.neatflix.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ericg.neatflix.R
import com.ericg.neatflix.sharedComposables.MovieGenreChip
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.ButtonColor
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun MovieDetails() {
    val movieGenre = remember {
        mutableStateListOf("Sci-fi", "Drama", "Fantasy")
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180E36))
    ) {
        val (
            headerImage,
            backButton,
            movieGenreChips,
            descriptionText,
            cast,
            movieTitleBox,
            moviePosterImage,
            translucentBr,
            ratingBar) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.dont_look_up),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .fillMaxHeight(0.33F)
                .constrainAs(headerImage) {},
            contentScale = Crop,
            contentDescription = "Header landscape image",
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
            IconButton(onClick = { }) {
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
                        ), startY = 0.1F
                    )
                )
                .constrainAs(translucentBr) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(headerImage.bottom)
                }
        )

        Column(
            modifier = Modifier
                .constrainAs(movieTitleBox) {
                    start.linkTo(moviePosterImage.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(headerImage.bottom)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            val t = "Don't Look Up"
            Text(
                text = t,
                modifier = Modifier.fillMaxWidth(0.5F),
                maxLines = 2,
                fontSize = 16.sp,
                fontWeight = SemiBold,
                color = Color.White.copy(alpha = 0.78F)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "2022",
                fontSize = 15.sp,
                fontWeight = Light,
                color = Color.White.copy(alpha = 0.56F)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.dont_look_up_portrait),
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .width(130.dp)
                .height(195.dp)
                .constrainAs(moviePosterImage) {
                    top.linkTo(headerImage.bottom)
                    bottom.linkTo(headerImage.bottom)
                    start.linkTo(parent.start)
                },
            contentScale = Crop,
            contentDescription = "movie poster"
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .constrainAs(ratingBar) {
                    start.linkTo(moviePosterImage.end, 0.dp)
                    end.linkTo(parent.end, 0.dp)
                    top.linkTo(headerImage.bottom)
                    bottom.linkTo(moviePosterImage.bottom)
                }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .border(1.dp, color = Color.White.copy(alpha = 0.78F))
                        .padding(4.dp)
                ) {
                    Text(
                        text = "16+",
                        fontSize = 14.sp,
                        fontWeight = Light,
                        color = Color.White.copy(alpha = 0.56F)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "2h 18m",
                    fontSize = 14.sp,
                    fontWeight = Light,
                    color = Color.White.copy(alpha = 0.56F)
                )
            }

            RatingBar(
                value = 4.0F,
                modifier = Modifier.padding(horizontal = 6.dp),
                config = RatingBarConfig()
                    .style(RatingBarStyle.HighLighted)
                    .isIndicator(true)
                    .activeColor(AppOnPrimaryColor)
                    .hideInactiveStars(false)
                    .inactiveColor(Color.LightGray)
                    .stepSize(StepSize.HALF)
                    .numStars(5)
                    .size(16.dp)
                    .padding(4.dp)
                    .style(RatingBarStyle.HighLighted),
                onValueChange = {},
                onRatingChanged = {}
            )

            var favorite by remember { mutableStateOf(true) }
            IconButton(onClick = {
                favorite = !favorite
            }) {
                Icon(
                    painter = painterResource(
                        id = if (favorite) R.drawable.ic_heart_fill
                        else R.drawable.ic_heart_outlie
                    ),
                    tint = AppOnPrimaryColor,
                    contentDescription = "fav icon"
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .padding(4.dp)
                .constrainAs(movieGenreChips) {
                    top.linkTo(moviePosterImage.bottom)
                    start.linkTo(parent.start)
                }
        ) {
            items(movieGenre) {
                MovieGenreChip(
                    background = ButtonColor,
                    textColor = AppOnPrimaryColor,
                    genre = it)
            }
        }

        ExpandableText(
            text = "Kate (Jennifer Lawrence), an astronomy grad student, " +
                    " and her professor Dr. Randall Mindy (Leonardo DiCaprio) make an" +
                    " astounding discovery of a comet orbiting within the solar system." +
                    " The problem: it's on a direct collision course with Earth. The other" +
                    " problem? No one really seems to care",
            modifier = Modifier
                .padding(4.dp)
                .constrainAs(descriptionText) {
                    top.linkTo(movieGenreChips.bottom)
                    start.linkTo(parent.start)
                }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .constrainAs(cast) {
                    top.linkTo(descriptionText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            item {
                Text(
                    text = "Cast",
                    fontWeight = Bold,
                    color = AppOnPrimaryColor,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            item {
                LazyRow {
                    items(10) {
                        CastCrew()
                    }
                }
            }
        }
    }
}

@Composable
fun CastCrew() {
    Column(
        modifier = Modifier.padding(end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            modifier = Modifier
                /*.border(
                    width = .5.dp,
                    color = Color(0XFF74e39a),
                    shape = CircleShape
                )*/
                .clip(CircleShape)
                .size(70.dp),
            imageModel = R.drawable.timothee,
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
            color = AppOnPrimaryColor
        )
        Text(
            text = "Actor",
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
    minimizedMaxLines: Int = 3,
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
        if (!expanded && textLayoutResult != null && seeMoreSize != null
            && lastLineIndex + 1 == textLayoutResult.lineCount
            && textLayoutResult.isLineEllipsized(lastLineIndex)
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
                color = AppOnPrimaryColor,
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

@Preview
@Composable
fun MovieDetailsPreview() {
    MovieDetails()
}