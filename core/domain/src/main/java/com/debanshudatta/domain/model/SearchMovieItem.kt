package com.debanshudatta.domain.model

import com.debanshudatta.data.model.SearchMovieDTO

data class SearchMovieItem(
    val id:Int,
    val name:String
)

internal fun SearchMovieDTO.toSearchMovieItem(): SearchMovieItem {
    return SearchMovieItem(
        id = this.id,
        name = this.title
    )
}