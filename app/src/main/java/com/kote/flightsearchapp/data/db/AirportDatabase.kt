package com.kote.flightsearchapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Airport::class, Favorite::class], version = 2, exportSchema = false)
abstract class AirportDatabase: RoomDatabase() {
    abstract fun airportDao(): AirportDao

    companion object {
        private var INSTANCE: AirportDatabase? = null

        fun getDataBase(context: Context): AirportDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, AirportDatabase::class.java, "airport_database")
                    .createFromAsset("database/flight_search.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}