package com.ericg.neatflix.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericg.neatflix.R
import com.ericg.neatflix.screens.destinations.HomeDestination
import com.ericg.neatflix.sharedComposables.BackButton
import com.ericg.neatflix.sharedComposables.SearchBar
import com.ericg.neatflix.sharedComposables.SearchResultItem
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.AppPrimaryColor
import com.ericg.neatflix.ui.theme.ButtonColor
import com.ericg.neatflix.util.Constants
import com.ericg.neatflix.util.collectAsStateLifecycleAware
import com.ericg.neatflix.viewmodel.WatchListViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Destination
@Composable
fun WatchList(
    navigator: DestinationsNavigator,
    watchListViewModel: WatchListViewModel = hiltViewModel()
) {
    var totalDismissed by remember { mutableStateOf(0) }

    val myWatchList = watchListViewModel.getFullWatchList()
        .collectAsStateLifecycleAware(initial = emptyList())

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

        fun countItems(items: Int): String {
            return when (items) {
                1 -> "Found 1 item"
                0 -> "There's nothing here!"
                else -> "Found $items items"
            }
        }

        var showNumberIndicator by remember { mutableStateOf(true) }
        AnimatedVisibility(visible = showNumberIndicator) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .border(
                        width = 1.dp, color = ButtonColor,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .background(ButtonColor.copy(alpha = 0.25F))
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = countItems(myWatchList.value.size),
                        color = AppOnPrimaryColor
                    )
                    IconButton(onClick = { showNumberIndicator = !showNumberIndicator }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cancel),
                            tint = AppOnPrimaryColor,
                            contentDescription = "Cancel button"
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(myWatchList.value, key = { it.mediaId }) { movie ->
                SwipeToDismissItem(
                    modifier = Modifier.animateItemPlacement(),
                    onDismiss = {
                        watchListViewModel.removeFromWatchList(movie.mediaId)
                        totalDismissed += 1
                    }) {
                    SearchResultItem(
                        title = movie.title,
                        posterImage = "${Constants.BASE_POSTER_IMAGE_URL}/${movie.imagePath}",
                        genres = emptyList(),
                        rating = movie.rating,
                        releaseYear = movie.releaseDate
                    ) { }
                }
            }
        }

        var openDialog by remember { mutableStateOf(true) }
        if (totalDismissed == 3 && openDialog && myWatchList.value.size > 1) {
            AlertDialog(
                title = { Text(text = "Delete All") },
                text = { Text(text = "Would you like to clear your watch list?") },
                shape = RoundedCornerShape(8.dp),
                confirmButton = {
                    TextButton(onClick = {
                        watchListViewModel.deleteWatchList()
                        openDialog = openDialog.not()
                    }) {
                        Text(text = "YES")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openDialog = openDialog.not() }) {
                        Text(text = "NO")
                    }
                },
                backgroundColor = ButtonColor,
                contentColor = AppOnPrimaryColor,
                onDismissRequest = {
                    openDialog = openDialog.not()
                })
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SwipeToDismissItem(
    modifier: Modifier,
    onDismiss: () -> Unit,
    swippable: @Composable () -> Unit,
) {
    val dismissState = DismissState(initialValue = DismissValue.Default,
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDismiss()
            }
            true
        })

    SwipeToDismiss(
        state = dismissState,
        modifier = modifier,
        background = {
            if (dismissState.dismissDirection == DismissDirection.EndToStart) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color.Transparent, ButtonColor.copy(alpha = 0.25F))
                            )
                        )
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color(0xFFFF6F6F),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        },
        dismissContent = {
            swippable()
        },
        directions = setOf(DismissDirection.EndToStart)
    )
}