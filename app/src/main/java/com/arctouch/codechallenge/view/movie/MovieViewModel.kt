package com.arctouch.codechallenge.view.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.model.data.Movie
import com.arctouch.codechallenge.model.repository.retrofit.TmdbRepository
import com.arctouch.codechallenge.view.util.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieViewModel(private val repository: TmdbRepository,
                     private val movieId: Long) : BaseViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    suspend fun getMovieById() = withContext(Dispatchers.IO) {
        _isLoading.postValue(true)

        try {
            val movie = repository.getMovieDetails(movieId)

            if (movie.isSuccessful) {
                _successRequest.postValue(true)
                _movie.postValue(movie.body())
            } else {
                _errorsRequest.postValue(true)
            }


        } catch (e: Exception) {
            _errorsRequest.postValue(true)
        } finally {
            _isLoading.postValue(false)
        }
    }


    class Factory(private val repository: TmdbRepository) : ViewModelProvider.Factory {
        private var movieId = -1L

        fun setMovieID(id: Long) {
            movieId = id
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieViewModel(repository, movieId) as T
        }
    }
}