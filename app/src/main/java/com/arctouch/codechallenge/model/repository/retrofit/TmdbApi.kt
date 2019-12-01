package com.arctouch.codechallenge.model.repository.retrofit

import com.arctouch.codechallenge.model.data.GenreResponse
import com.arctouch.codechallenge.model.data.Movie
import com.arctouch.codechallenge.model.data.UpcomingMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("genre/movie/list")
    suspend fun genres(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Response<GenreResponse>

    @GET("movie/upcoming")
    suspend fun upcomingMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Long,
            @Query("region") region: String
    ): Response<UpcomingMoviesResponse>

    @GET("movie/{id}")
    suspend fun movie(
            @Path("id") id: Long,
            @Query("api_key") apiKey: String,
            @Query("language") language: String
    ): Response<Movie>
}
