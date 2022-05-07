package com.ericg.neatflix.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import com.ericg.neatflix.screens.destinations.HomeDestination
import com.ericg.neatflix.sharedComposables.BackButton
import com.ericg.neatflix.sharedComposables.SearchBar
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun WatchList(
    navigator: DestinationsNavigator
) {
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
                .fillMaxWidth()
        ) {
            val focusManager = LocalFocusManager.current
            BackButton {
                focusManager.clearFocus()
                navigator.navigateUp()
            }

            Text(
                text = "My Watch list",
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = AppOnPrimaryColor
            )

            IconButton(
                onClick = {
                    focusManager.clearFocus()
                    navigator.popBackStack()
                    navigator.navigate(HomeDestination()) {
                        this.launchSingleTop = true
                        this.restoreState
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(id = R.drawable.ic_home),
                    tint = AppOnPrimaryColor,
                    contentDescription = "home icon"
                )
            }
        }

        SearchBar(
            autoFocus = false,
            onSearch = {
            }
        )

        val focusManager = LocalFocusManager.current
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(count = 10) {
                /* SearchResultItem(
                     imdbId = "sample id",
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
                         Timber.d("Remove from Room DB")
                     },
                     onClick = {
                         focusManager.clearFocus()
                         *//*navigator.navigate(MovieDetailsDestination() {
                            this.launchSingleTop = true
                        }*//*
                    }
                )*/
            }
        }
    }
}
