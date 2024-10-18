package com.kote.flightsearchapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kote.flightsearchapp.data.db.Airport
import com.kote.flightsearchapp.navigation.NavigationDestination
import com.kote.flightsearchapp.ui.components.SearchTopBar
import androidx.compose.ui.Modifier
import com.kote.flightsearchapp.data.db.Favorite
import com.kote.flightsearchapp.ui.components.FlightCard

object FlightDestination: NavigationDestination {
    override val rout = "flight_details"
    override val titleRes = 0
    const val itemIdArg = "itemIdArg"
    val routeWithArgs = "${rout}/{$itemIdArg}"
}

@Composable
fun FlightDetailScreen(
    onNavigateUp: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = AppViewModelProvider.FACTORY)
) {
    val arrivalAirports by viewModel.arrivalAirports.collectAsState()
    val departureAirport by viewModel.departureAirport.collectAsState()
    val favorites by viewModel.favorites.collectAsState()

    if (departureAirport != null) {
        Scaffold(
            topBar = {
                SearchTopBar(
                    title = "Flight Details",
                    canNavigateBack = true,
                    navigateUp = onNavigateUp
                )
            }
        ) { innerPadding ->
            SelectedFlights(
                destinations = arrivalAirports,
                departure = departureAirport!!,
                toggleFavorite = viewModel::toggleFavorite,
                favorites = favorites,
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
    }
}

@Composable
fun SelectedFlights(
    destinations: List<Airport>,
    departure: Airport,
    toggleFavorite: (String, String) -> Unit,
    favorites: List<Favorite>,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(destinations) {destination ->
                val isFavorite = favorites.find {
                    it.destinationCode == destination.iataCode && it.departureCode == departure.iataCode
                }
                FlightCard(
                    destinationAirport = destination,
                    departureAirport = departure,
                    toggleFavorite = toggleFavorite,
                    isFavorite = isFavorite != null
                )
            }
        }
    }
}
