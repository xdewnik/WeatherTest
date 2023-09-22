package com.koolya.weathertest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.koolya.weathertest.feature.map.ui.MapScreen
import com.koolya.weathertest.feature.weather.ui.WeatherListScreen


//todo refactor navigation to more multimodule approach
@Composable
fun AppNavigation(navController: NavHostController, startDestination: Route) {
    NavHost(navController = navController, startDestination = startDestination.path) {
        composable(route = Route.WeatherList.path) {
            WeatherListScreen {
                navController.navigate(Route.Map)
            }
        }
        composable(route = Route.Map.path) {
            MapScreen()
        }
    }
}

private fun NavHostController.navigate(route: Route) {
    when (route) {
        Route.PopUp -> popBackStack()
        else -> navigate(route.path)
    }
}


sealed class Route(val path: String) {
    data object WeatherList : Route("weather_list")
    data object Map : Route("map")
    data object PopUp : Route("")
}