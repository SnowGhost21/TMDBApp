package com.arctouch.codechallenge.view.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.databinding.MovieItemBinding
import com.arctouch.codechallenge.model.data.Movie
import com.arctouch.codechallenge.view.databinding.ReactiveAdapter

class MoviesAdapter: ReactiveAdapter<Movie, MovieItemBinding>() {

    private val _movieDetail = MutableLiveData<MovieNavigationParam>()
    val movieDetail: LiveData<MovieNavigationParam> = _movieDetail

    override fun getBinding(context: Context, parent: ViewGroup, viewType: Int): MovieItemBinding {
        return MovieItemBinding.inflate(LayoutInflater.from(context), parent, false)
    }

    override fun bind(binding: MovieItemBinding, item: Movie) {
        val viewModel = MovieItemViewModel(item)
        binding.viewModel = viewModel

        ViewCompat.setTransitionName(binding.cardView, item.id.toString())
        ViewCompat.setTransitionName(binding.posterImageView, "image_${item.id}")

        setNavigationClickListener(binding, item)
    }

    private fun setNavigationClickListener(binding: MovieItemBinding, item: Movie) {
        val sharedViews = listOf(binding.cardView,
                binding.posterImageView)

        binding.cardView.setOnClickListener {
            _movieDetail.postValue(MovieNavigationParam(item.id, item.title, sharedViews))
        }
    }
}

data class MovieNavigationParam(val movieId: Long, val movieTitle: String, val sharedViews: List<View>)