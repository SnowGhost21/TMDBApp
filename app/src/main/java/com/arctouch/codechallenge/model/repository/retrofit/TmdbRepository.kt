package com.arctouch.codechallenge.model.repository.retrofit

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.model.data.GenreResponse
import com.arctouch.codechallenge.model.data.Movie
import com.arctouch.codechallenge.model.data.UpcomingMoviesResponse
import retrofit2.Response

class TmdbRepository(private val api: TmdbApi) {

    suspend fun getMoviesGenre(page: Int): Response<GenreResponse> {
        return api.genres(BuildConfig.API_KEY, BuildConfig.DEFAULT_LANGUAGE, page)
    }

    suspend fun upcomingMovies(page: Long): Response<UpcomingMoviesResponse> {
        return api.upcomingMovies(BuildConfig.API_KEY, BuildConfig.DEFAULT_LANGUAGE, page, BuildConfig.DEFAULT_REGION)
    }

    suspend fun getMovieDetails(id: Long): Response<Movie> {
        return api.movie(id, BuildConfig.API_KEY, BuildConfig.DEFAULT_LANGUAGE)
    }
}