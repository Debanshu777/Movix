package com.debanshudatta.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debanshudatta.domain.model.MovieDetails
import com.debanshudatta.domain.usecases.GetMovieDetailsById
import com.debanshudatta.network.domain.Result
import com.debanshudatta.network.domain.error.NetworkError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieDetailsViewModel @Inject constructor(private val getMovieDetails: GetMovieDetailsById) :
    ViewModel() {
    private val _movieDetails = MutableStateFlow<DataState<MovieDetails>>(DataState.Uninitialized)
    val movieDetails = _movieDetails.asStateFlow()

    fun getMovieById(id: String) {
        _movieDetails.tryEmit(DataState.Loading)
        viewModelScope.launch {
            when (val response = getMovieDetails.execute(id)) {
                is Result.Error -> _movieDetails.tryEmit(
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

                is Result.Success -> _movieDetails.tryEmit(
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