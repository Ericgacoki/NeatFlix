package com.ericg.neatflix.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericg.neatflix.R
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.ericg.neatflix.ui.theme.ButtonColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun Home() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF180E36))
    ) {
        ProfileAndSearchBar()
        NestedScroll()
    }
}

@Composable
fun ProfileAndSearchBar() {
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
                    .clip(CircleShape),
                contentDescription = "profile"
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
                contentDescription = "profile"
            )

            val types = listOf("Movies", "Tv Shows")
            var selectedType by remember { mutableStateOf(types[0]) }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                types.forEachIndexed { index, filmType ->
                    Text(
                        text = filmType,
                        fontWeight = if (selectedType == types[index]) FontWeight.Bold else FontWeight.Light,
                        fontSize = if (selectedType == types[index]) 24.sp else 16.sp,
                        color = if (selectedType == types[index])
                            AppOnPrimaryColor else Color.LightGray.copy(alpha = 0.78F),
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp, top = 8.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                selectedType = types[index]
                            }

                    )
                }
            }

            val animOffset = animateDpAsState(
                targetValue = when (types.indexOf(selectedType)) {
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
            onClick = { }
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

/*@Composable
fun DropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Movies", "Series")
    var selectedIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxHeight()
            .width(80.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFF180E36))
    ) {
        Text(
            items[selectedIndex],
            color = Color.White.copy(alpha = 0.78F),
            modifier = Modifier
                .align(Center)
                .padding(4.dp)
                .clickable(onClick = { expanded = true }),
            textAlign = TextAlign.Center
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(ButtonColor)
                .clip(RoundedCornerShape(4.dp))
        ) {
            items.forEachIndexed { index, value ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    selectedIndex = index
                }) {
                    Text(
                        text = value,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                    )
                }
                if (index != items.lastIndex) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp)
                            .height((0.5).dp)
                            .background(Color(0XFF9495B1))
                    )
                }
            }
        }
    }
}*/

@Composable
fun NestedScroll() {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState, modifier = Modifier
            .padding(horizontal = 2.dp)
            .fillMaxSize()
    ) {
        item {
            Text(
                text = "Genres",
                fontSize = 24.sp,
                color = AppOnPrimaryColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )
        }
        item {
            // TODO: Get genres from API and append them to "All"
            val genres = listOf("All", "Drama", "Romance", "Action", "Horror", "Sci-Fi", "Crime")
            var selectedGenre by remember { mutableStateOf("All") }

            LazyRow(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                items(count = genres.size) {
                    SelectableGenreChip(
                        genre = genres[it],
                        selected = genres[it] == selectedGenre
                    ) {
                        selectedGenre = genres[it]
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
                modifier = Modifier.padding(4.dp)
            )
        }
        item {
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(count = 10) {
                    MovieItem(
                        landscape = true,
                        image = R.drawable.dont_look_up,
                        title = "Don't Look Up",
                        modifier = Modifier
                            /** 4:3 */
                            .width(215.dp)
                            .height(161.25.dp)
                    )
                }
            }
        }

        item {
            Text(
                text = "Top Rated",
                fontSize = 24.sp,
                color = AppOnPrimaryColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )
        }
        item {
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(count = 10) {
                    MovieItem(
                        image = R.drawable.manifest,
                        title = "",
                        /** 2:3 */
                        modifier = Modifier
                            .width(130.dp)
                            .height(195.dp)
                    )
                }
            }
        }

        item {
            Text(
                text = "Now Playing",
                fontSize = 24.sp,
                color = AppOnPrimaryColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )
        }
        item {
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(count = 10) {
                    MovieItem(
                        image = R.drawable.dont_look_up,
                        title = "",
                        modifier = Modifier
                            .width(130.dp)
                            .height(195.dp)
                    )
                }
            }
        }

        item {
            Text(
                text = "Upcoming",
                fontSize = 24.sp,
                color = AppOnPrimaryColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )
        }
        item {
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(count = 10) {
                    MovieItem(
                        image = R.drawable.dont_look_up,
                        title = "",
                        modifier = Modifier
                            .width(130.dp)
                            .height(195.dp)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MovieItem(
    image: Int,
    title: String,
    modifier: Modifier,
    landscape: Boolean = false
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(all = 4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        CoilImage(
            imageModel = image,
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
            modifier = modifier.clip(RoundedCornerShape(8.dp)),
            contentDescription = "Movie item"
        )

        AnimatedVisibility(visible = landscape) {
            Text(
                text = title,
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

/**This [SelectableGenreChip()] differs from [MovieGenreChip()] in that
 * it is selectable while MovieGenreChip is not*/
@Composable
fun SelectableGenreChip(
    genre: String,
    selected: Boolean,
    onclick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(end = 4.dp)
            .clip(CircleShape)
            .background(
                color = if (selected) Color(0xFFA0A1C2)
                else ButtonColor.copy(alpha = 0.5F)
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
    Home()
}