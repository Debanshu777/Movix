package com.debanshudatta.details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.debanshudatta.details.MovieDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsScreenNavigation(
    val movieId: String,
)

fun NavController.navigateToDetailsScreen(movieId: String, navOptions: NavOptions? = null) =
    navigate(route = MovieDetailsScreenNavigation(movieId), navOptions)

fun NavGraphBuilder.forDetailsScreen() {
    composable<MovieDetailsScreenNavigation> { backStackEntry ->
        val movieId = backStackEntry.arguments?.getString("movieId")
        MovieDetailsScreen(movieId!!)
    }
}