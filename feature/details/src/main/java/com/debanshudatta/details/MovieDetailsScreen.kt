package com.debanshudatta.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.debanshudatta.domain.model.MovieDetails

@Composable
internal fun MovieDetailsScreen(
    movieId:String,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(movieId) {
        viewModel.getMovieById(movieId)
    }
    val movieDetailsState by viewModel.movieDetails.collectAsStateWithLifecycle()
    MovieDetailsScreen(movieDetailsState)
}

@Composable
internal fun MovieDetailsScreen(searchDetailsState: DataState<MovieDetails>) {
    when (searchDetailsState) {
        is DataState.Loading -> {
            Text("Loading...")
        }

        is DataState.Error -> {
            Text("Error: ${searchDetailsState.error}")
        }

        is DataState.Success -> {
            MovieDetailsItem(searchDetailsState.data)
        }

        is DataState.Uninitialized -> {
            Text("Uninitialized")
        }
    }
}


@Composable
fun MovieDetailsItem(movie: MovieDetails) {
    Column {
        Text(text = movie.name)
        Text(text = movie.description)
    }
}