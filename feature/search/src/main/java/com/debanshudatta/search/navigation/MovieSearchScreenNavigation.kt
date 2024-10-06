package com.debanshudatta.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.debanshudatta.search.MovieSearchScreen
import kotlinx.serialization.Serializable

@Serializable
data object MovieSearchScreenNavigation

fun NavController.navigateToSearchScreen(navOptions: NavOptions) =
    navigate(route = MovieSearchScreenNavigation, navOptions)

fun NavGraphBuilder.forSearchScreen(onItemClick: (String) -> Unit) {
    composable<MovieSearchScreenNavigation> {
        MovieSearchScreen(onItemClick)
    }
}