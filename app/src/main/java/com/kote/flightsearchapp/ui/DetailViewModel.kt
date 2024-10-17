package com.kote.flightsearchapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kote.flightsearchapp.data.airport.AirportRepository
import com.kote.flightsearchapp.data.airport.db.Airport
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val airportRepository: AirportRepository
): ViewModel() {
    private val itemIataCode: String = checkNotNull(savedStateHandle[FlightDestination.itemIdArg])
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
}