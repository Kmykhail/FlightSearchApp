package com.kote.flightsearchapp.data.airport

import com.kote.flightsearchapp.data.airport.db.Airport
import com.kote.flightsearchapp.data.airport.db.AirportDao
import kotlinx.coroutines.flow.Flow

class OfflineAirportRepository(private val airportDao: AirportDao): AirportRepository {
    override fun getAllAirports(): Flow<List<Airport>> = airportDao.getAllAirports()
    override fun getAirportByIata(iataCode: String) : Flow<Airport> = airportDao.getAirportByIata(iataCode)
    override fun getAirportsByName(name: String) : Flow<List<Airport>> = airportDao.getAirportsByName(name)
    override fun getAirport(id: Int): Flow<Airport> = airportDao.getAirport(id)
    override suspend fun getMatchedAirports(search: String): List<Airport> = airportDao.getMatchedAirports(search)
    override fun getArrivalAirports(iataCode: String): Flow<List<Airport>> = airportDao.getArrivalAirports(iataCode)
}