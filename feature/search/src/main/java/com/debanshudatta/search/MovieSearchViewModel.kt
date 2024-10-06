package com.debanshudatta.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debanshudatta.domain.model.SearchMovieItem
import com.debanshudatta.domain.usecases.GetMoviesByQuery
import com.debanshudatta.network.domain.Result
import com.debanshudatta.network.domain.error.NetworkError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieSearchViewModel @Inject constructor(private val getMoviesByQuery: GetMoviesByQuery) :
    ViewModel() {
    private val _searchMoviesList =
        MutableStateFlow<DataState<List<SearchMovieItem>>>(DataState.Uninitialized)
    val searchMovieList = _searchMoviesList.asStateFlow()

    fun getSearchedMovies(query: String) {
        _searchMoviesList.tryEmit(DataState.Loading)
        viewModelScope.launch {
            when (val response = getMoviesByQuery.execute(query)) {
                is Result.Error -> _searchMoviesList.tryEmit(
                    when (response.error) {
                        NetworkError.REQUEST_TIMEOUT -> DataState.Error("REQUEST_TIMEOUT")
                        NetworkError.UNAUTHORIZED -> DataState.Error("UNAUTHORIZED")
                        NetworkError.CONFLICT -> DataState.Error("CONFLICT")
                        NetworkError.SERIALIZATION -> DataState.Error("SERIALIZATION")
                        NetworkError.NO_INTERNET -> DataState.Error("NO_INTERNET")
                        NetworkError.PAYLOAD_TOO_LARGE -> DataState.Error("PAYLOAD_TOO_LARGE")
                        NetworkError.SERVER_ERROR -> DataState.Error("SERVER_ERROR")
                        NetworkError.UNKNOWN -> DataState.Error("UNKNOWN")
                    }
                )

                is Result.Success -> _searchMoviesList.tryEmit(
                    DataState.Success(response.data)
                )
            }
        }
    }
}

sealed interface DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>
    data class Error(val error: String) : DataState<Nothing>
    data object Loading : DataState<Nothing>
    data object Uninitialized : DataState<Nothing>
}