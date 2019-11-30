package com.arctouch.codechallenge.view.movie

import android.text.TextUtils
import com.arctouch.codechallenge.model.data.Movie

class MovieItemViewModel(private val movie: Movie) {

    val imagePath = movie.posterPath

    val title = movie.title

    val genresOverview: String = if (movie.genres != null) TextUtils.join(",", movie.genres) else "No genre defined"

    val releaseDate = movie.releaseDate


}