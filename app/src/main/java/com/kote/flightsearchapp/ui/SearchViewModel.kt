package com.kote.flightsearchapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kote.flightsearchapp.data.AirportRepository
import com.kote.flightsearchapp.data.db.Airport
import com.kote.flightsearchapp.data.db.Favorite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.forEach
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

    private val favoritesFlow: StateFlow<List<Favorite>> = airportRepository.getAllFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    private val airportsFlow: StateFlow<List<Airport>> = airportRepository.getAllAirports().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            favoritesFlow.collect{ collectedFavorites ->
                _uiState.update { it.copy(favorites = collectedFavorites) }
            }
        }

        viewModelScope.launch {
            airportsFlow.collect { collectedAirports ->
                _uiState.update { it.copy(airports = collectedAirports) }
            }
        }
    }

    fun searchAirport(input: String) {
        _uiState.update { it.copy(userInput = input) }
        if (input.isNotEmpty() && input.isNotBlank()) {
            viewModelScope.launch {
                val matchedList = airportRepository.getMatchedAirports(input)
                _uiState.update { it.copy(matchedAirports = matchedList, showResult = ShowResult.SEARCH) }
            }
        }
    }

    fun toggleFavorite(departureCode: String, destinationCode: String) = viewModelScope.launch {
        val favorite = Favorite(departureCode = departureCode, destinationCode = destinationCode)
        val searchedFavorite = favoritesFlow.value.find {
            it.departureCode == favorite.departureCode && it.destinationCode == favorite.destinationCode
        }
        if (searchedFavorite == null) {
            airportRepository.saveFavorite(favorite)
        } else {
            airportRepository.removeFavorite(searchedFavorite)
        }
    }

    fun getAirportByCode(iataCode: String) : Airport? {
        return _uiState.value.airports.find { it.iataCode == iataCode }
    }
}

data class SearchUiState(
    val userInput: String = "",
    val matchedAirports: List<Airport> = emptyList(),
    val airports: List<Airport> = emptyList(),
    val favorites: List<Favorite> = emptyList(),
    val showResult: ShowResult = ShowResult.FAVORITE
)