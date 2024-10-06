package com.debanshudatta.data.repository

import com.debanshudatta.data.model.MovieDetailsDTO
import com.debanshudatta.data.model.MovieSearchResponse
import com.debanshudatta.data.model.SearchMovieDTO
import com.debanshudatta.network.domain.Result
import com.debanshudatta.network.domain.error.NetworkError

interface MovieDataRepository {
    suspend fun searchMovies(query:String): Result<MovieSearchResponse, NetworkError>
    suspend fun getMovieDetails(id:String): Result<MovieDetailsDTO, NetworkError>
}