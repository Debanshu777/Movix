package com.debanshudatta.data.repository

import com.debanshudatta.data.model.MovieDetailsDTO
import com.debanshudatta.data.model.MovieSearchResponse
import com.debanshudatta.network.domain.Result
import com.debanshudatta.network.domain.error.NetworkError
import com.debanshudatta.network.domain.usecase.ClientWrapper
import javax.inject.Inject

class RemoteMovieDataRepository @Inject constructor(private val clientWrapper: ClientWrapper) : MovieDataRepository {
    override suspend fun searchMovies(query: String): Result<MovieSearchResponse, NetworkError> {
        return clientWrapper.networkGetUsecase<MovieSearchResponse>("search/movie", mapOf("query" to query))
    }

    override suspend fun getMovieDetails(id: String): Result<MovieDetailsDTO, NetworkError> {
        return clientWrapper.networkGetUsecase<MovieDetailsDTO>("movie/$id",null)
    }
}