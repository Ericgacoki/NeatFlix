package com.ericg.neatflix.sharedComposables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericg.neatflix.R
import com.ericg.neatflix.ui.theme.AppOnPrimaryColor
import com.ericg.neatflix.ui.theme.ButtonColor
import com.ericg.neatflix.viewmodel.SearchViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchBar(
    autoFocus: Boolean,
    viewModel: SearchViewModel = hiltViewModel(),
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

        LaunchedEffect(key1 = searchInput) {
            if (viewModel.searchParam.value.trim().isNotEmpty() &&
                viewModel.searchParam.value.trim().length != viewModel.previousSearch.value.length
            ) {
                delay(750)
                onSearch()
                viewModel.previousSearch.value = searchInput.trim()
            }
        }

        TextField(
            value = searchInput,
            onValueChange = { newValue ->
                searchInput = if (newValue.trim().isNotEmpty()) newValue else ""
                viewModel.searchParam.value = searchInput
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
                    if (viewModel.searchParam.value.trim().isNotEmpty()) {
                        focusManager.clearFocus()
                        viewModel.searchParam.value = searchInput
                        if (searchInput != viewModel.previousSearch.value) {
                            viewModel.previousSearch.value = searchInput
                            onSearch()
                        }
                    }
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
                            viewModel.searchParam.value = ""
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                tint = AppOnPrimaryColor,
                                contentDescription = null
                            )
                        }
                    }

                    IconButton(onClick = {
                        if (viewModel.searchParam.value.trim().isNotEmpty()) {
                            focusManager.clearFocus()
                            viewModel.searchParam.value = searchInput
                            if (searchInput != viewModel.previousSearch.value) {
                                viewModel.previousSearch.value = searchInput
                                onSearch()
                            }
                        }
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
