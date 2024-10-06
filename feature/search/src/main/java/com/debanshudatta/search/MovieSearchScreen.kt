package com.debanshudatta.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.debanshudatta.domain.model.SearchMovieItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
internal fun MovieSearchScreen(
    onItemClick: (String) -> Unit,
    viewModel: MovieSearchViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("Harry Potter") }
    var searchJob by remember { mutableStateOf<Job?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(searchQuery) {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            delay(300)
            viewModel.getSearchedMovies(searchQuery)
        }
    }

    val searchedMovieListState by viewModel.searchMovieList.collectAsStateWithLifecycle()

    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
        MovieSearchScreen(onItemClick, searchedMovieListState)
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Movies") },
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .padding(16.dp)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun MovieSearchScreen(
    onItemClick: (String) -> Unit,
    searchedMovieListState: DataState<List<SearchMovieItem>>
) {
    when (searchedMovieListState) {
        is DataState.Loading -> {
            Text("Loading...")
        }

        is DataState.Error -> {
            Text("Error: ${searchedMovieListState.error}")
        }

        is DataState.Success -> {
            val movieList = searchedMovieListState.data
            LazyColumn(
                modifier = Modifier.imeNestedScroll()
            ) {
                items(movieList, key = { movie -> movie.id }) { movie ->
                    MovieItem(
                        Modifier.clickable { onItemClick(movie.id.toString()) },
                        movie = movie
                    )
                }
            }
        }

        is DataState.Uninitialized -> {
            Text("Uninitialized")
        }
    }
}


@Composable
fun MovieItem(modifier: Modifier, movie: SearchMovieItem) {
    Text(modifier = modifier, text = movie.name)
}