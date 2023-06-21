package com.example.nexttogo.ui.nexttogo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexttogo.data.repo.RaceService
import com.example.nexttogo.ui.NextToGoState
import com.example.nexttogo.ui.toRaceSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NextToGoViewModel @Inject constructor(
    private val raceService: RaceService,
) : ViewModel() {
    private var _state = MutableStateFlow(NextToGoState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch { load() }
    }

    private suspend fun load() {
        _state.update { it.copy(isLoading = true, error = null) }
        try {
            raceService
                .getAllRaces()
                .collectLatest { allRaceDetails ->
                    _state.update { nextToGoState ->
                        val allRaceSummaries = allRaceDetails.data.raceSummaries
                            .map { it.value.toRaceSummary() }
                            .sortedBy { it.startTime }
                            .take(5)
                            .toImmutableList()
                        nextToGoState.copy(
                            data = NextToGoState.Data(
                                raceSummaries = allRaceSummaries,
                                localRaceSummaries = allRaceSummaries
                            ),
                            isLoading = false,
                            error = null,
                            filteredByCategory = null,
                        )
                    }
                }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e("NextToGoViewModel", "Error Loading Data")
            _state.update { it.copy(error = NextToGoState.Error(e), isLoading = false) }
        }
    }

    fun refresh() {
        Log.d("NextToGoViewModel", "refresh")
        viewModelScope.launch {
            load()
        }
    }

    fun filterByCategory(category: NextToGoState.RaceCategory) {
        Log.d("NextToGoViewModel", "filterByCategory")
        _state.update { nextToGoState ->
          //  if (nextToGoState.data == null) return
            nextToGoState.copy(
                data = nextToGoState.data?.copy(
                    localRaceSummaries = nextToGoState.data.raceSummaries
                        .filter { it.category == category }
                        .toImmutableList()),
                filteredByCategory = category
            )
        }
    }
}