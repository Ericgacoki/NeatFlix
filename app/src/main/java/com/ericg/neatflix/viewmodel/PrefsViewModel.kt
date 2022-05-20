package com.ericg.neatflix.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericg.neatflix.data.repository.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrefsViewModel @Inject constructor(private val prefsRepo: PrefsRepository) : ViewModel() {

    fun updateIncludeAdult(includeAdult: Boolean) {
        viewModelScope.launch {
            prefsRepo.updateIncludeAdult(includeAdult)
        }
    }

    val includeAdult: State<Flow<Boolean?>> = mutableStateOf(prefsRepo.readIncludeAdult())
}
