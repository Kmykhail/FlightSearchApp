package com.kote.flightsearchapp.data.airport.db

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * FROM airport ORDER BY name ASC")
    fun getAllAirports(): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code = :iataCode")
    fun getAirportByIata(iataCode: String) : Flow<Airport>

    @Query("SELECT * FROM airport WHERE name = :name")
    fun getAirportsByName(name: String) : Flow<List<Airport>>

    @Query("SELECT * from airport WHERE id = :id")
    fun getAirport(id: Int): Flow<Airport>

    @Query(
        """
            SELECT *
            FROM airport
            WHERE name LIKE '%' || :search || '%'
            OR iata_code LIKE '%' || :search || '%'
            ORDER BY passengers ASC
        """
    )
    suspend fun getMatchedAirports(search: String): List<Airport>

    @Query("SELECT * FROM airport WHERE iata_code != :iataCode")
    fun getArrivalAirports(iataCode: String): Flow<List<Airport>>
}
