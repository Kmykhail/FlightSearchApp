@file:OptIn(ExperimentalMaterial3Api::class)

package com.kote.flightsearchapp.ui

import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kote.flightsearchapp.R
import com.kote.flightsearchapp.navigation.NavigationDestination
import com.kote.flightsearchapp.ui.components.SearchField
import com.kote.flightsearchapp.ui.components.SearchListResult

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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        SearchField(
            userInput = uiState.userInput,
            onSearch = viewModel::searchAirport,
        )
        when(uiState.showResult) {
            ShowResult.SEARCH -> SearchListResult(
                airports = uiState.airports,
                onAirportClick = navToFlightDetails,
                modifier = modifier.padding(vertical = 4.dp)
            )
            ShowResult.FAVORITE -> {}
            else -> Unit
        }
//        if (favoriteList.isEmpty()) {
//            Text(text = "No favorites yet")
//        } else {
//            LazyColumn(
//                contentPadding = contentPadding,
//                modifier = modifier
//            ) {
//                items(favoriteList) {favorite ->
//                    FlightNumber(
//                        flightNumber = favorite,
//                        toggleFavorite = {},
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 6.dp, vertical = 6.dp)
//                    )
//                }
//            }
//        }
    }
}

@Composable
private fun FlightNumber(
    flightNumber: String,
    toggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(
            topEnd = 10.dp,
            bottomStart = 10.dp
        ),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "DEPART",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.50f)
                    ),
                    textAlign = TextAlign.Justify
                )
                Row {
                    Text(text = "flightNumber.iata_code")
                    Text(text = flightNumber)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "ARRIVE",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.50f)
                    )
                )
                Row {
                    Text(text = "flightNumber.iata_code")
                    Text(text = flightNumber)
                }
            }

            IconButton(
                onClick = toggleFavorite,
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                )
            }
        }
    }
}
