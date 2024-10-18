package com.kote.flightsearchapp.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kote.flightsearchapp.data.db.Airport
import com.kote.flightsearchapp.data.db.Favorite
import kotlinx.coroutines.flow.Flow

interface AirportRepository {
    // airport
    fun getAllAirports(): Flow<List<Airport>>
    fun getAirportByIata(iataCode: String) : Flow<Airport>
    fun getAirportsByName(name: String) : Flow<List<Airport>>
    fun getAirport(id: Int): Flow<Airport>
    suspend fun getMatchedAirports(search: String): List<Airport>
    fun getArrivalAirports(iataCode: String): Flow<List<Airport>>

    // favorite
    fun getAllFavorites(): Flow<List<Favorite>>
    suspend fun saveFavorite(favorite: Favorite)
    suspend fun removeFavorite(favorite: Favorite)
}