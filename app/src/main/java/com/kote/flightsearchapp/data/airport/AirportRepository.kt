package com.kote.flightsearchapp.data.airport

import com.kote.flightsearchapp.data.airport.db.Airport
import kotlinx.coroutines.flow.Flow

interface AirportRepository {
    fun getAllAirports(): Flow<List<Airport>>
    fun getAirportByIata(iataCode: String) : Flow<Airport>
    fun getAirportsByName(name: String) : Flow<List<Airport>>
    fun getAirport(id: Int): Flow<Airport>
    suspend fun getMatchedAirports(search: String): List<Airport>
    fun getArrivalAirports(iataCode: String): Flow<List<Airport>>
}