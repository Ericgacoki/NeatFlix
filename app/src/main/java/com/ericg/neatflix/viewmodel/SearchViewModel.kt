package com.ericg.neatflix.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ericg.neatflix.data.repository.PrefsRepository
import com.ericg.neatflix.data.repository.SearchRepository
import com.ericg.neatflix.model.Film
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val prefsRepo: PrefsRepository
) : ViewModel() {
    private var _searchMovies = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val searchMoviesState: State<Flow<PagingData<Film>>> = _searchMovies

    val includeAdult: State<Flow<Boolean?>> = mutableStateOf(prefsRepo.readIncludeAdult())

    var searchParam = mutableStateOf("")
    var previousSearch = mutableStateOf("")
    var searchParamState: State<String> = searchParam

    init {
        searchParam.value = ""
    }

    fun searchRemoteMovie(includeAdult: Boolean) {
        viewModelScope.launch {
            _searchMovies.value = if (searchParam.value.isNotEmpty()) {
                searchRepository.multiSearch(searchParams = searchParam.value, includeAdult)
                    .cachedIn(viewModelScope)
            } else {
                searchRepository.multiSearch(searchParams = searchParam.value, includeAdult)
                    .cachedIn(viewModelScope)
            }
        }
    }
}