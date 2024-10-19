@file:OptIn(ExperimentalMaterial3Api::class)

package com.kote.flightsearchapp.ui

import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kote.flightsearchapp.R
import com.kote.flightsearchapp.data.db.Airport
import com.kote.flightsearchapp.data.db.Favorite
import com.kote.flightsearchapp.navigation.NavigationDestination
import com.kote.flightsearchapp.ui.components.FlightCard
import com.kote.flightsearchapp.ui.components.SearchField
import com.kote.flightsearchapp.ui.components.SearchListResult
import com.kote.flightsearchapp.ui.components.SearchTopBar

object SearchDestination: NavigationDestination {
    override val rout = "search_screen"
    override val titleRes = R.string.app_name
}

@Composable
fun FlightSearchScreen(
    navToFlightDetails: (String) -> Unit,
    viewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.FACTORY),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            SearchTopBar(
                title = stringResource(SearchDestination.titleRes),
                canNavigateBack = false
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SearchField(
                userInput = uiState.userInput,
                onSearch = viewModel::searchAirport,
            )
            Box(modifier = modifier.padding(vertical = 4.dp)) {
                if (uiState.matchedAirports.isEmpty() && uiState.favorites.isNotEmpty()) {
                    FavoriteList(
                        favorites = uiState.favorites,
                        getAirport = viewModel::getAirportByCode,
                        toggleFavorite = viewModel::toggleFavorite
                    )
                }
                if (uiState.matchedAirports.isNotEmpty()) {
                    SearchListResult(
                        airports = uiState.matchedAirports,
                        onAirportClick = navToFlightDetails,
                        modifier = modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteList(
    favorites: List<Favorite>,
    getAirport: (String) -> Airport?,
    toggleFavorite: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier.padding(8.dp)
    ) {
        items(favorites) {favorite ->
            val destination = getAirport(favorite.destinationCode)
            val departure = getAirport(favorite.departureCode)
            if (destination != null && departure != null) {
                FlightCard(
                    destinationAirport = destination,
                    departureAirport = departure,
                    toggleFavorite = toggleFavorite,
                    isFavorite = true
                )
            }
        }
    }
}