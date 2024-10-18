package com.kote.flightsearchapp.data

import com.kote.flightsearchapp.data.db.Airport
import com.kote.flightsearchapp.data.db.AirportDao
import com.kote.flightsearchapp.data.db.Favorite
import kotlinx.coroutines.flow.Flow

class OfflineAirportRepository(private val airportDao: AirportDao): AirportRepository {
    override fun getAllAirports(): Flow<List<Airport>> = airportDao.getAllAirports()
    override fun getAirportByIata(iataCode: String) : Flow<Airport> = airportDao.getAirportByIata(iataCode)
    override fun getAirportsByName(name: String) : Flow<List<Airport>> = airportDao.getAirportsByName(name)
    override fun getAirport(id: Int): Flow<Airport> = airportDao.getAirport(id)
    override suspend fun getMatchedAirports(search: String): List<Airport> = airportDao.getMatchedAirports(search)
    override fun getArrivalAirports(iataCode: String): Flow<List<Airport>> = airportDao.getArrivalAirports(iataCode)

    // favorite
    override fun getAllFavorites(): Flow<List<Favorite>> = airportDao.getAllFavorites()
    override suspend fun saveFavorite(favorite: Favorite) = airportDao.saveFavorite(favorite)
    override suspend fun removeFavorite(favorite: Favorite) = airportDao.removeFavorite(favorite)
}