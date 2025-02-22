package com.nunkison.globoplaymobilechallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nunkison.globoplaymobilechallenge.R
import com.nunkison.globoplaymobilechallenge.project.structure.MoviesRepository
import com.nunkison.globoplaymobilechallenge.project.structure.MoviesViewModel
import com.nunkison.globoplaymobilechallenge.project.structure.MoviesViewModel.UiState
import com.nunkison.globoplaymobilechallenge.stringResource
import com.nunkison.globoplaymobilechallenge.ui.movies.data.MoviesScreenSuccessState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModelImpl(
    private val repo: MoviesRepository
) : ViewModel(), MoviesViewModel {
    private val _loadingState = MutableStateFlow(false)
    override val loadingState: StateFlow<Boolean> = _loadingState

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState.Success(MoviesScreenSuccessState(false, arrayListOf()))
    )
    override val uiState: StateFlow<UiState> = _uiState

    private var favoriteFilterEnable: Boolean = false
    private var loadDelayedJob: Job? = null

    override val searchQuery = MutableStateFlow("")
    override val searchModeEnable = MutableStateFlow(false)

    init {
        loadMovies()
    }

    override fun loadMovies() {
        viewModelScope.launch(IO) {
            _loadingState.emit(true)
            try {
                val data = if (favoriteFilterEnable) {
                    repo.getCurrentFavorites()
                } else if (searchQuery.value.isNotEmpty()) {
                    repo.searchVideos(searchQuery.value)
                } else {
                    repo.getDiscoverMovies()
                }
                if (data.isEmpty() || data[0].movieCovers.isEmpty()) {
                    _uiState.emit(UiState.Empty)
                } else {
                    _uiState.emit(
                        UiState.Success(
                            MoviesScreenSuccessState(
                                favoriteFilterEnable = favoriteFilterEnable,
                                data = data
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.emit(
                    UiState.Error(
                        exception = e,
                        message = e.message ?: stringResource(R.string.generic_error)
                    )
                )
            }
            _loadingState.value = false
        }
    }

    override fun toogleFilterByFavorites() {
        favoriteFilterEnable = !favoriteFilterEnable
        loadMovies()
    }

    override fun loadMoviesDelayed() {
        loadDelayedJob?.cancel()
        loadDelayedJob = viewModelScope.launch {
            delay(900)
            loadMovies()
        }
    }

    override fun onCleared() {
        super.onCleared()
        loadDelayedJob?.cancel()
    }
}