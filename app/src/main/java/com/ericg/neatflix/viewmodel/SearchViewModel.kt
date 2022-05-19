package com.ericg.neatflix.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ericg.neatflix.data.repository.SearchRepository
import com.ericg.neatflix.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private var _searchMovies = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val searchMoviesState: State<Flow<PagingData<Movie>>> = _searchMovies

    var searchParam = mutableStateOf("")
    var previousSearch = mutableStateOf("")
    var searchParamState: State<String> = searchParam

    init{
        searchParam.value = ""
    }

    fun searchRemoteMovie() {
        viewModelScope.launch {
            _searchMovies.value = if (searchParam.value.isNotEmpty()) {
                searchRepository.multiSearch(searchParams = searchParam.value)
                    .cachedIn(viewModelScope)
            } else {
                searchRepository.multiSearch(searchParams = searchParam.value)
                    .cachedIn(viewModelScope)
            }
        }
    }
}