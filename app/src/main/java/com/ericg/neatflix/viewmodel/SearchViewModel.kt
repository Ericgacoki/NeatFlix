package com.ericg.neatflix.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.ericg.neatflix.data.repository.PrefsRepository
import com.ericg.neatflix.data.repository.SearchRepository
import com.ericg.neatflix.model.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    prefsRepo: PrefsRepository
) : ViewModel() {
    private var _multiSearch = mutableStateOf<Flow<PagingData<Search>>>(emptyFlow())
    val multiSearchState: State<Flow<PagingData<Search>>> = _multiSearch

    val includeAdult: State<Flow<Boolean?>> = mutableStateOf(prefsRepo.readIncludeAdult())

    var searchParam = mutableStateOf("")
    var previousSearch = mutableStateOf("")
    var searchParamState: State<String> = searchParam

    init {
        searchParam.value = ""
    }

    fun searchRemoteMovie(includeAdult: Boolean) {
        viewModelScope.launch {
            if (searchParam.value.isNotEmpty()) {
                _multiSearch.value = searchRepository.multiSearch(
                    searchParams = searchParam.value,
                    includeAdult
                ).map { result ->
                    result.filter {
                        ((it.title != null || it.originalName != null || it.originalTitle != null) &&
                                (it.mediaType == "tv" || it.mediaType == "movie"))
                    }
                }.cachedIn(viewModelScope)
            }
        }
    }
}