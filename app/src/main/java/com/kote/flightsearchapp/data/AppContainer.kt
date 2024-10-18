package com.kote.flightsearchapp.data

import android.content.Context
import com.kote.flightsearchapp.data.db.AirportDatabase

interface AppContainer {
    val airportRepository: AirportRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val airportRepository: AirportRepository by lazy {
        OfflineAirportRepository(AirportDatabase.getDataBase(context).airportDao())
    }
}