package com.kote.flightsearchapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kote.flightsearchapp.data.AirportRepository
import com.kote.flightsearchapp.data.db.Airport
import com.kote.flightsearchapp.data.db.Favorite
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val airportRepository: AirportRepository
): ViewModel() {
    private val itemIataCode: String = checkNotNull(savedStateHandle[FlightDestination.itemIdArg])

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    val departureAirport: StateFlow<Airport?> = airportRepository.getAirportByIata(itemIataCode).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    val arrivalAirports: StateFlow<List<Airport>> = airportRepository.getArrivalAirports(itemIataCode).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    val favorites: StateFlow<List<Favorite>> = airportRepository.getAllFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    fun toggleFavorite(departureCode: String, destinationCode: String) = viewModelScope.launch {
        val favorite = Favorite(departureCode = departureCode, destinationCode = destinationCode)
        val searchedFavorite = favorites.value.find {
            it.departureCode == favorite.departureCode && it.destinationCode == favorite.destinationCode
        }
        if (searchedFavorite == null) {
            airportRepository.saveFavorite(favorite)
        } else {
            airportRepository.removeFavorite(searchedFavorite)
        }
    }

    fun checkIfFavorite(departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            val favoriteExists = favorites.value.any {
                it.departureCode == departureCode && it.destinationCode == destinationCode
            }
            _isFavorite.update { favoriteExists }
        }
    }
}