package com.lunchmenu.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lunchmenu.home.data.repository.LunchMenuRepository
import com.lunchmenu.home.domain.usecase.ConvertLunchMenuForCalendarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This controls all the state of the screen to avoid any side effects
 */
sealed interface HomeState {
    data object Loading : HomeState
    data object Error : HomeState
    data class ShowMenu(val list: MutableList<Pair<String, String>>) : HomeState
}

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val lunchMenuForCalendarUseCase: ConvertLunchMenuForCalendarUseCase,
    private val repository: LunchMenuRepository
): ViewModel() {
    private val _mapOfWeeksAndMenu = MutableStateFlow<HomeState>(HomeState.Loading)
    val mapOfWeeksAndMenu = _mapOfWeeksAndMenu.asStateFlow()

    init {
        loadMenu()
    }

    private fun loadMenu() {
        viewModelScope.launch {
            _mapOfWeeksAndMenu.emit(HomeState.Loading)
        }
        viewModelScope.launch(Dispatchers.IO) {
            lunchMenuForCalendarUseCase.invoke().collectLatest {
                _mapOfWeeksAndMenu.emit(HomeState.ShowMenu(it))
            }
        }
    }

    fun loadYelp() {
        viewModelScope.launch {
            val list = repository.getYelpBeerBusinesses()
            Log.i(TAG, "loadYelp: $list")
        }
    }
}