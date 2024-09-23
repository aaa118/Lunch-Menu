package com.lunchmenu.home.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.lunchmenu.home.data.datasource.remote.HomeRemoteWorker
import com.lunchmenu.home.data.repository.LunchMenuRepository
import com.lunchmenu.home.domain.usecase.ConvertLunchMenuForCalendarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
        viewModelScope.launch(Dispatchers.IO) {
            lunchMenuForCalendarUseCase.invoke().collectLatest {
                if (it.isNotEmpty()) _mapOfWeeksAndMenu.emit(HomeState.ShowMenu(it))
                else _mapOfWeeksAndMenu.emit(HomeState.Loading)
            }
        }
    }

    fun loadYelp() {
        viewModelScope.launch {
//            val list = repository.getYelpBeerBusinesses()
//            Log.i(TAG, "loadYelp: $list")
        }
    }
}