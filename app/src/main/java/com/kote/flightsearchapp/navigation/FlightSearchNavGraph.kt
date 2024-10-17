package com.kote.flightsearchapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kote.flightsearchapp.ui.FlightDestination
import com.kote.flightsearchapp.ui.FlightDetailScreen
import com.kote.flightsearchapp.ui.FlightSearchScreen
import com.kote.flightsearchapp.ui.SearchDestination

interface NavigationDestination {
    val rout: String
    val titleRes: Int
}

@Composable
fun FlightSearchNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SearchDestination.rout
    ) {
        composable(route = SearchDestination.rout) {
            FlightSearchScreen(
                navToFlightDetails = {
                    navController.navigate("${FlightDestination.rout}/$it")
                }
            )
        }
        composable(
            route = FlightDestination.routeWithArgs,
            arguments = listOf(navArgument(FlightDestination.itemIdArg) {
                type = NavType.StringType
            })
        ) {
            FlightDetailScreen(
                onNavigateUp = {
                    navController.navigate(SearchDestination.rout)
                }
            )
        }
    }
}