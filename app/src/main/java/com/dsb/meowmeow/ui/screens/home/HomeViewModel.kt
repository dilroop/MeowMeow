package com.dsb.meowmeow.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsb.meowmeow.data.CatsRepository
import com.dsb.meowmeow.data.models.ApiRequestFilter.*
import com.dsb.meowmeow.data.models.ApiResultFilter.NoHatsFilter
import com.dsb.meowmeow.model.CatModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor() : ViewModel() {
    val uiState = mutableStateOf<HomeScreenState>(HomeScreenState.Loading)
    val animatedImageFilter = mutableStateOf(AnimatedImageFilter(enable = true))
    val staticImageFilter = mutableStateOf(StaticImageFilter(enable = false))
    val noHatsFilter = mutableStateOf(NoHatsFilter(enable = true))
    val onlyHatsFilter = mutableStateOf(OnlyHatsImagesFilter(enable = false))

    @Inject
    lateinit var catsRepository: CatsRepository
    private lateinit var gifLoadingJob: Job

    fun loadCats() {
        if(::gifLoadingJob.isInitialized && gifLoadingJob.isActive) gifLoadingJob.cancel()
        uiState.value = HomeScreenState.Loading
        gifLoadingJob = viewModelScope.launch {
            val requestFilters = listOfNotNull(
                animatedImageFilter.value,
                staticImageFilter.value,
                onlyHatsFilter.value
            )
            val resultFilters = listOfNotNull(noHatsFilter.value)
            val cats = catsRepository.getCats(
                apiRequestFilters = requestFilters,
                apiResultFilters = resultFilters
            )
            uiState.value = HomeScreenState.Content(cats)
        }
    }

    fun updateAnimatedImageFilter(enable: Boolean) = withReload {
        animatedImageFilter.value = animatedImageFilter.value.copy(enable = enable)
    }

    fun updateStaticImageFilter(enable: Boolean) = withReload {
        staticImageFilter.value = staticImageFilter.value.copy(enable = enable)
    }

    fun updateNoHatsImageFilter(enable: Boolean) = withReload {
        noHatsFilter.value = noHatsFilter.value.copy(enable = enable)
    }

    fun updateOnlyHatsImageFilter(enable: Boolean) = withReload {
        onlyHatsFilter.value = onlyHatsFilter.value.copy(enable = enable)
    }

    private fun withReload(block: () -> Unit) {
        block()
        loadCats()
    }
}

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Content(val cats: List<CatModel>) : HomeScreenState()
}