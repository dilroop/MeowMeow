package com.dsb.meowmeow.ui.screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsb.meowmeow.data.CatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor() : ViewModel() {
    companion object {
        private const val INITIAL_LOAD_SIZE = 9
    }

    val uiState = mutableStateOf(HomeScreenState.LOADING)

    @Inject
    lateinit var catsRepository: CatsRepository

    fun loadCats() {
        uiState.value = HomeScreenState.LOADING
        viewModelScope.launch {
            delay(2000)
            val cats = catsRepository.getCats(INITIAL_LOAD_SIZE)
            Log.d("Cats", cats.joinToString(separator = ", "))
            uiState.value = HomeScreenState.LOADED
        }
    }
}

enum class HomeScreenState {
    LOADING, LOADED
}