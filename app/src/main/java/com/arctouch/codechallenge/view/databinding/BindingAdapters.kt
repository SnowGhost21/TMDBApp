package com.arctouch.codechallenge.view.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.R
import com.bumptech.glide.Glide

private val POSTER_URL = "https://image.tmdb.org/t/p/w154"
private val BACKDROP_URL = "https://image.tmdb.org/t/p/w780"


@BindingAdapter("buildPosterUrl")
fun buildPosterUrl(view: ImageView, posterPath: String?) {
    posterPath.let {
        Glide
                .with(view.context)
                .load(POSTER_URL + it + "?api_key=" + BuildConfig.API_KEY)
                .error(R.drawable.ic_image_placeholder)
                .into(view)
    }
}

@BindingAdapter("visibleOrGone")
fun visibleOrGone(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("buildBackdropUrl")
fun buildBackdropUrl(view: ImageView, backdropPath: String?) {
    backdropPath.let {
        Glide
                .with(view.context)
                .load(BACKDROP_URL + it + "?api_key=" + BuildConfig.API_KEY)
                .error(R.drawable.ic_image_placeholder)
                .into(view)
    }
}

