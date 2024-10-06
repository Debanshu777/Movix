package com.debanshudatta.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieSearchResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<SearchMovieDTO>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)