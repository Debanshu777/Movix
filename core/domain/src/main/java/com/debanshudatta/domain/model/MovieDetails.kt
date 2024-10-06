package com.debanshudatta.domain.model

import com.debanshudatta.data.model.MovieDetailsDTO

data class MovieDetails(
    val id:Int,
    val name:String,
    val description:String,
)

internal fun MovieDetailsDTO.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = this.id,
        name = this.title,
        description = this.overview
    )
}