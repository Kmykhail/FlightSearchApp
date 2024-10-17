package com.kote.flightsearchapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kote.flightsearchapp.FlightSearchApplication

object AppViewModelProvider {
    val FACTORY = viewModelFactory {
        initializer {
            SearchViewModel(
                flightApplication().container.airportRepository
            )
        }

        initializer {
            DetailViewModel(
                this.createSavedStateHandle(),
                flightApplication().container.airportRepository
            )
        }
    }
}

fun CreationExtras.flightApplication(): FlightSearchApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)