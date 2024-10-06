package com.debanshudatta.domain.usecases

import com.debanshudatta.data.repository.MovieDataRepository
import com.debanshudatta.domain.model.MovieDetails
import com.debanshudatta.domain.model.toMovieDetails
import com.debanshudatta.network.domain.Result
import com.debanshudatta.network.domain.error.NetworkError
import javax.inject.Inject

class GetMovieDetailsById @Inject constructor(private val repository: MovieDataRepository) {
    suspend fun execute(id: String): Result<MovieDetails, NetworkError> {
        return when (val data = repository.getMovieDetails(id)) {
            is Result.Success -> {
                val searchMovieList = data.data.toMovieDetails()
                Result.Success(searchMovieList)
            }
            is Result.Error -> Result.Error(data.error)
        }
    }
}
