package com.debanshudatta.domain.usecases

import com.debanshudatta.data.repository.MovieDataRepository
import com.debanshudatta.data.repository.RemoteMovieDataRepository
import com.debanshudatta.domain.model.SearchMovieItem
import com.debanshudatta.domain.model.toSearchMovieItem
import com.debanshudatta.network.domain.Result
import com.debanshudatta.network.domain.error.NetworkError
import javax.inject.Inject

class GetMoviesByQuery @Inject constructor(private val repository: MovieDataRepository) {
    suspend fun execute(query:String):Result<List<SearchMovieItem>,NetworkError>{
       return when (val data = repository.searchMovies(query)) {
            is Result.Success -> {
                val searchMovieList = data.data.results.map{ it.toSearchMovieItem() }
                Result.Success(searchMovieList)
            }
            is Result.Error -> Result.Error(data.error)
        }
    }
}
