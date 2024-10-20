package com.kote.flightsearchapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kote.flightsearchapp.data.AirportRepository
import com.kote.flightsearchapp.data.UserPreferencesRepository
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

class SearchViewModel(
    private val airportRepository: AirportRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    var userInput by mutableStateOf("")

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
            userPreferencesRepository.getSavedUserInput().collect{
                if (it.isNotBlank()) {
                    searchAirport(it)
                }
            }
        }

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
                userPreferencesRepository.saveUserInputPreference(userInput = input)
                val matchedList = airportRepository.getMatchedAirports(input)
                _uiState.update { it.copy(matchedAirports = matchedList, showResult = ShowResult.SEARCH) }
            }
        }
        if (input.isEmpty()) {
            _uiState.update { it.copy(matchedAirports = emptyList()) }
        }
    }

    fun removeFavorite(departureCode: String, destinationCode: String) = viewModelScope.launch{
        favoritesFlow.value.find { it.departureCode == departureCode && it.destinationCode == destinationCode }
            ?.let { airportRepository.removeFavorite(it) }
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