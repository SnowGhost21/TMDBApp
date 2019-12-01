package com.arctouch.codechallenge.dependency

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.model.repository.retrofit.TmdbApi
import com.arctouch.codechallenge.model.repository.retrofit.TmdbRepository
import com.arctouch.codechallenge.view.home.HomeViewModel
import com.arctouch.codechallenge.view.movie.MovieViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    factory {

        val logging = HttpLoggingInterceptor()
        logging.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()
    }

    factory { GsonBuilder().create() }

    factory {
        Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(get()))
                .client(get())
                .build()
    }

    factory {
        val retrofit: Retrofit = get()
        retrofit.create(TmdbApi::class.java)
    }

    single { TmdbRepository(get()) }

    viewModel { HomeViewModel(get()) }

    factory { MovieViewModel.Factory(get()) }
}