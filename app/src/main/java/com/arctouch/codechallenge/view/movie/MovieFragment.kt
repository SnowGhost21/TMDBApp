package com.arctouch.codechallenge.view.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.FragmentMovieBinding
import com.arctouch.codechallenge.model.data.Genre
import com.arctouch.codechallenge.view.MainActivity
import com.arctouch.codechallenge.view.databinding.buildBackdropUrl
import com.arctouch.codechallenge.view.databinding.buildPosterUrl
import org.koin.android.ext.android.inject

class MovieFragment : Fragment() {

    private val factory: MovieViewModel.Factory by inject()
    private val viewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this, factory)
                .get(MovieViewModel::class.java)
    }

    private val args: MovieFragmentArgs by navArgs()

    init {
        lifecycleScope.launchWhenCreated {
            viewModel.getMovieById()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(requireContext())
                .inflateTransition(R.transition.default_transition)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMovieBinding.inflate(inflater, container, false)
        val movieId = args.movieId
        val movieName = args.movieName

        (activity as MainActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = movieName

        }
        factory.setMovieID(movieId)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.movie.observe(this, Observer { movie ->
            createTags(binding, movie.genres)
            binding.description.text = movie.title
            binding.description.text = if (!movie.overview.isNullOrEmpty()) movie.overview else "Descrição não disponível"
            movie.posterPath?.let { image ->
                buildPosterUrl(binding.moviePoster, image)
            }
            movie.backdropPath?.let {
                buildBackdropUrl(binding.buildBackdropUrl, it)
            }

            movie.releaseDate?.let {
                binding.releaseDate.text = "Lançamento: $it"
            }
        })

        ViewCompat.setTransitionName(binding.coordinator, movieId.toString())
        ViewCompat.setTransitionName(binding.moviePoster, "image_$movieId")


        return binding.root
    }

    private fun createTags(binding: FragmentMovieBinding, genres: ArrayList<Genre>?) {

        genres?.let {
            it.forEach { genre ->
                binding.tags.addChip(genre.name)
            }
        }


    }

}