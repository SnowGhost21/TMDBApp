package com.arctouch.codechallenge.model

import com.arctouch.codechallenge.model.data.Genre
import com.arctouch.codechallenge.model.data.Movie
import com.arctouch.codechallenge.model.data.UpcomingMoviesResponse

val createComicGenre = Genre(1, "Comedia")

val createDramaGenre = Genre(2, "Drama")

val createListGenre = arrayListOf(createComicGenre, createDramaGenre)

val createListGenreId = arrayListOf(1,2)

val createMovie = Movie(1L,
        "Title",
        "Description",
        createListGenre,
        createListGenreId,
        "poser",
        "backdrop",
        "2019-12-12")

val createListMovie = arrayListOf(createMovie)

val createMovieResponse = UpcomingMoviesResponse(1, createListMovie, 2, 20)