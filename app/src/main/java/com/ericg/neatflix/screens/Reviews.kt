package com.ericg.neatflix.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ericg.neatflix.R
import com.ericg.neatflix.data.remote.response.Review
import com.ericg.neatflix.sharedComposables.BackButton
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.ericg.neatflix.ui.theme.ButtonColor
import com.ericg.neatflix.util.Constants
import com.ericg.neatflix.util.FilmType
import com.ericg.neatflix.viewmodel.ReviewsViewModel
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
fun ReviewsScreen(
    navigator: DestinationsNavigator,
    reviewsViewModel: ReviewsViewModel = hiltViewModel(),
    filmType: FilmType,
    filmId: Int,
    filmTitle: String?
) {
    val reviews = reviewsViewModel.filmReviews.value.collectAsLazyPagingItems()

    LaunchedEffect(key1 = filmId) {
        reviewsViewModel.getFilmReview(filmId, filmType)
    }

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
            BackButton {
                navigator.navigateUp()
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${reviews.itemCount}",
                    modifier = Modifier
                        .padding(start = 50.dp)
                        .background(shape = CircleShape, color = ButtonColor)
                        .padding(horizontal = 4.dp),
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = AppOnPrimaryColor
                )
                Text(
                    text = if (reviews.itemCount != 1) "Reviews" else "Review",
                    modifier = Modifier.padding(start = 4.dp),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = AppOnPrimaryColor
                )
            }
        }

        Text(
            text = filmTitle ?: "",
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = AppOnPrimaryColor
        )

        LazyColumn {
            items(reviews) { review ->
                ReviewItem(item = review)
            }
        }
    }
}

@Composable
fun ReviewItem(item: Review?) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {

        Row(horizontalArrangement = Arrangement.Center) {
            CoilImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp),
                imageModel = "${Constants.BASE_POSTER_IMAGE_URL}${item?.authorDetails?.avatarPath}",
                shimmerParams = ShimmerParams(
                    baseColor = AppPrimaryColor,
                    highlightColor = ButtonColor,
                    durationMillis = 500,
                    dropOff = 0.65F,
                    tilt = 20F
                ),
                failure = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(id = R.drawable.ic_user),
                            tint = Color.LightGray,
                            contentDescription = null
                        )
                    }
                },
                previewPlaceholder = R.drawable.ic_user,
                contentScale = ContentScale.Crop,
                circularReveal = CircularReveal(duration = 1000),
                contentDescription = "author image"
            )

            Column(
                modifier = Modifier.padding(start = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = if (item?.authorDetails?.name?.isNotEmpty() == true) {
                        item.authorDetails.name
                    } else "Anonymous",
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = AppOnPrimaryColor
                )

                Text(
                    text = "@${item?.authorDetails?.userName}",
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp,
                    color = AppOnPrimaryColor.copy(alpha = 0.5F)
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(ButtonColor)
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp, start = 8.dp, end = 8.dp)
        ) {
            Column(verticalArrangement = SpaceBetween) {
                SelectionContainer {
                    Text(
                        text = item?.content ?: "Review not found!",
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        fontSize = 15.sp,
                        color = AppOnPrimaryColor
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = SpaceBetween
                ) {
                    RatingBar(
                        value = (item?.authorDetails?.rating?.div(2))?.toFloat() ?: 0.toFloat(),
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

                    Text(
                        text = item?.createdOn?.removeRange(10..item.createdOn.lastIndex) ?: "",
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        color = AppOnPrimaryColor.copy(alpha = 0.75F)
                    )
                }
            }
        }
    }
}
