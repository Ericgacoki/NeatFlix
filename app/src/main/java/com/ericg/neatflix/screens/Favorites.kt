package com.ericg.neatflix.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import timber.log.Timber

@Composable
fun Favorites() {
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
            BackButton {

            }

            Text(
                text = "My Favorites",
                modifier = Modifier.padding(start = 50.dp),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = AppOnPrimaryColor
            )

        }

        SearchBar(
            autoFocus = false,
            onSearch = {
                Timber.d("Search Param = $globalExposedSearchParam")
            })

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(count = 10) {
                SearchResultItem(
                    imdbId = "",
                    title = "Spider-Man far from home and never coming back",
                    posterImage = R.drawable.manifest,
                    genres = listOf(
                        MovieGenre(1, "Drama"),
                        MovieGenre(2, "Sci-Fi"),
                        MovieGenre(3, "Romance"),
                        MovieGenre(4, "Action"),
                    ),
                    rating = 4F,
                    releaseYear = "2019",
                    showFavorite = true,
                    onRemoveFavorite = {
                        Timber.d("Removing favorite...")
                    },
                    onClick = {
                        Timber.d("Navigating to details screen...")
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun FavoritesPrev() {
    Favorites()
}