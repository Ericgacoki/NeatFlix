package com.ericg.neatflix.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericg.neatflix.R
import com.ericg.neatflix.data.MovieGenre
import com.ericg.neatflix.sharedComposables.BackButton
import com.ericg.neatflix.sharedComposables.SearchBar
import com.ericg.neatflix.sharedComposables.SearchResultItem
import com.ericg.neatflix.sharedComposables.globalExposedSearchParam
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppPrimaryColor)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 16.dp)
                .fillMaxWidth()
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
                Timber.d("Search Param = $globalExposedSearchParam")
            })

        // FIXME: Display UI status -> loading, no data...
        if (10 <= 0) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.83F)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_match_found),
                    contentDescription = null
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {

            items(count = 20) {
                SearchResultItem(
                    imdbId = "",
                    title = "Spider-Man far from home",
                    posterImage = R.drawable.manifest,
                    genres = listOf(
                        MovieGenre(1, "Drama"),
                        MovieGenre(2, "Sci-Fi"),
                        MovieGenre(3, "Romance"),
                        MovieGenre(4, "Action"),
                    ),
                    rating = 4F,
                    releaseYear = "2019",
                    onRemoveFavorite = {},
                    onClick = {

                    }
                )
            }
        }

    }
}

@Preview
@Composable
fun SearchPrev() {
    // SearchScreen()
}