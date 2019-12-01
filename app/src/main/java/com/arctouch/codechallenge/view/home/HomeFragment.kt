package com.arctouch.codechallenge.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.databinding.FragmentHomeBinding
import com.arctouch.codechallenge.view.MainActivity
import com.arctouch.codechallenge.view.movie.MovieNavigationParam
import com.arctouch.codechallenge.view.movie.MoviesAdapter
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

    init {
        lifecycleScope.launchWhenCreated {
            viewModel.loadMovies()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        Log.e("Fragment", "Home")

        (activity as MainActivity).supportActionBar?.apply {
            title = "The movies"
            setDisplayHomeAsUpEnabled(false)
        }

        val adapter = MoviesAdapter()
        adapter.movieDetail.observe(this, Observer {
            openCharacterDetailFragment(it)
        })

        binding.moviesRecyclerView.adapter = adapter
        binding.moviesRecyclerView.addItemDecoration(ListItemDecoration())

        viewModel.movies.observe(this, Observer { movies ->
            adapter.setItems(movies)
        })

        binding.moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as? LinearLayoutManager
                manager?.let {
                    val visibleItemCount = it.childCount
                    val totalItemCount = it.itemCount

                    val firstVisibleItemPosition = it.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        lifecycleScope.launch {
                            viewModel.loadMovies()
                        }
                    }
                }
            }

        })

        return binding.root
    }

    private fun openCharacterDetailFragment(params: MovieNavigationParam) {
        val characterId = params.movieId
        val characterName = params.movieTitle
        val action = HomeFragmentDirections.actionHomeFragmentToMovieFragment(characterId, characterName)


        val extras = FragmentNavigator.Extras.Builder().apply {
            params.sharedViews.forEach {
                addSharedElement(it, ViewCompat.getTransitionName(it) ?: "")
            }
        }.build()

        findNavController().navigate(action, extras)
    }
}
