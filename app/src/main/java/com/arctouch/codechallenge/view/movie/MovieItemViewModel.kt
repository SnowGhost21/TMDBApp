package com.arctouch.codechallenge.view.movie

import com.arctouch.codechallenge.model.data.Movie

class MovieItemViewModel(private val movie: Movie) {

    val imagePath = movie.posterPath

    val title = movie.title

    val releaseDate = movie.releaseDate


}