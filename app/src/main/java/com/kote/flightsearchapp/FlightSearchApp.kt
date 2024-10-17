package com.kote.flightsearchapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kote.flightsearchapp.navigation.FlightSearchNavGraph
import java.security.AccessController

@Composable
fun FlightSearchApp(navController: NavHostController = rememberNavController()) {
    FlightSearchNavGraph(navController = navController)
}