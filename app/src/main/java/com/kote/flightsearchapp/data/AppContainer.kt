package com.kote.flightsearchapp.data

import android.app.Application
import android.content.Context
import com.kote.flightsearchapp.data.airport.AirportRepository
import com.kote.flightsearchapp.data.airport.OfflineAirportRepository
import com.kote.flightsearchapp.data.airport.db.AirportDatabase

interface AppContainer {
    val airportRepository: AirportRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val airportRepository: AirportRepository by lazy {
        OfflineAirportRepository(AirportDatabase.getDataBase(context).airportDao())
    }
}