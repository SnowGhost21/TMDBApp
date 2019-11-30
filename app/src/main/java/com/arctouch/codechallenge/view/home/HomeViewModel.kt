package com.arctouch.codechallenge.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.model.data.Movie
import com.arctouch.codechallenge.model.repository.retrofit.TmdbRepository
import com.arctouch.codechallenge.view.util.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: TmdbRepository) : BaseViewModel() {

    companion object {
        private var PAGE = 1L;
        private var TOTAL_PAGES = 1
    }

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies


    suspend fun loadMovies() = withContext(Dispatchers.IO) {

        try {
            if (PAGE > TOTAL_PAGES) {
                return@withContext
            }

            _isLoading.postValue(true)

            val response = repository.upcomingMovies(PAGE)

            if (response.isSuccessful) {
                response.body()?.let {
                    _movies.postValue(it.results)
                    TOTAL_PAGES = it.totalPages
                    PAGE++
                    _successRequest.postValue(true)

                }
            } else {
                _errorsRequest.postValue(true)
            }
        } catch (e: Exception) {
            _errorsRequest.postValue(true)
        } finally {
            _isLoading.postValue(false)
        }
    }
}