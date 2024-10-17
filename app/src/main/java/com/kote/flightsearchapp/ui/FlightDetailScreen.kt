package com.kote.flightsearchapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kote.flightsearchapp.data.airport.db.Airport
import com.kote.flightsearchapp.navigation.NavigationDestination
import com.kote.flightsearchapp.ui.components.SearchTopBar
import androidx.compose.ui.Modifier

object FlightDestination: NavigationDestination {
    override val rout = "flight_details"
    override val titleRes = 0
    const val itemIdArg = "itemIdArg"
    val routeWithArgs = "${rout}/{$itemIdArg}"
}

@Composable
fun FlightDetailScreen(
    onNavigateUp: (String) -> Unit,
    viewModel: DetailViewModel = viewModel(factory = AppViewModelProvider.FACTORY)
) {
    val arrivalAirports by viewModel.arrivalAirports.collectAsState()
    val departureAirport by viewModel.departureAirport.collectAsState()
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
                destinationAirports = arrivalAirports,
                departureAirport = departureAirport!!,
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
    }

}
//
@Composable
fun SelectedFlights(
    destinationAirports: List<Airport>,
    departureAirport: Airport,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        items(destinationAirports) {destination ->
            Flights(destinationAirport = destination, departureAirport = departureAirport, toggleFavorite = { /*TODO*/ })
        }
    }
}

@Composable
private fun Flights(
    destinationAirport: Airport,
    departureAirport: Airport,
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
                modifier = androidx.compose.ui.Modifier
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
                    Text(text = departureAirport.iataCode)
                    Text(text = departureAirport.name)
                }
                Spacer(modifier = androidx.compose.ui.Modifier.height(8.dp))
                Text(
                    text = "ARRIVE",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.50f)
                    )
                )
                Row {
                    Text(text = destinationAirport.iataCode)
                    Text(text = destinationAirport.name)
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