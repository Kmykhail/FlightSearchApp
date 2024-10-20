package com.kote.flightsearchapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kote.flightsearchapp.data.db.Airport
import com.kote.flightsearchapp.ui.theme.FlightSearchAppTheme

@Composable
fun SearchField(
    userInput: String,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = userInput,
        onValueChange = { onSearch(it) },
        placeholder = { Text(text = "Enter departure airport") },
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (userInput.isNotEmpty()) {
                IconButton(onClick = { onSearch("") }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = CircleShape,
        modifier = modifier
    )
}

@Composable
fun SearchListResult(
    airports: List<Airport>,
    onAirportClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        items(
            items = airports, key = {it.id}
        ) { airport ->
            SearchResult(
                airport = airport,
                onAirportClick = { onAirportClick(airport.iataCode) }
            )
        }
    }
}

@Composable
private fun SearchResult(
    airport: Airport,
    onAirportClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onAirportClick() }
    ) {
        Text(
            text = airport.iataCode,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            modifier = modifier.weight(1f)
        )
        Text(
            text = airport.name,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.60f)
            ),
            maxLines = 1,
            modifier = modifier.weight(6f)
        )
    }
}

@Preview
@Composable
fun SearchFieldPreview(
) {
    FlightSearchAppTheme {
        SearchField(userInput = "", onSearch = {})
    }
}