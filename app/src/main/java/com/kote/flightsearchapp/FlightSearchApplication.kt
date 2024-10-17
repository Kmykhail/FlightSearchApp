package com.kote.flightsearchapp

import android.app.Application
import com.kote.flightsearchapp.data.AppContainer
import com.kote.flightsearchapp.data.AppDataContainer

class FlightSearchApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}