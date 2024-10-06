package com.debanshudatta.movie1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.debanshudatta.details.navigation.forDetailsScreen
import com.debanshudatta.details.navigation.navigateToDetailsScreen
import com.debanshudatta.search.navigation.MovieSearchScreenNavigation
import com.debanshudatta.search.navigation.forSearchScreen

@Composable
fun MovieNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = MovieSearchScreenNavigation,
    ) {
        forSearchScreen(navHostController::navigateToDetailsScreen)
        forDetailsScreen()
    }
}