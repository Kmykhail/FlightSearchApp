package com.kote.flightsearchapp.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kote.flightsearchapp.data.airport.AirportRepository
import com.kote.flightsearchapp.data.airport.db.Airport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val TIME_OUT = 5_000L

enum class ShowResult {
    SEARCH, FAVORITE
}

class SearchViewModel(private val airportRepository: AirportRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

//    val airportUiState: StateFlow<AirportUiState> =
//        airportRepository.getAllAirports().map { AirportUiState(it) }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIME_OUT),
//                initialValue = AirportUiState()
//            )

    fun searchAirport(input: String) {
        _uiState.update { it.copy(userInput = input) }
        if (input.isNotEmpty() && input.isNotBlank()) {
            viewModelScope.launch {
                val matchedList = airportRepository.getMatchedAirports(input)
                _uiState.update { it.copy(airports = matchedList, showResult = ShowResult.SEARCH) }
            }
        }
    }
}

data class SearchUiState(
    val userInput: String = "",
    val airports : List<Airport> = emptyList(),
    val showResult: ShowResult = ShowResult.FAVORITE
)