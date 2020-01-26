package com.arctouch.codechallenge.view.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arctouch.codechallenge.model.createMovieResponse
import com.arctouch.codechallenge.model.repository.retrofit.TmdbApi
import com.arctouch.codechallenge.model.repository.retrofit.TmdbRepository
import com.diegocunha.marvelheroguide.helper.assertLiveDataEquals
import com.diegocunha.marvelheroguide.helper.assertLiveDataNull
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val api = mock<TmdbApi>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `Get a list with upcoming movies with successful`() = runBlocking {
        `when`(api.upcomingMovies(any(), any(), any(), any())).thenReturn(Response.success(createMovieResponse))
        val repository = TmdbRepository(api)
        val viewModel = HomeViewModel(repository)
        withContext(Dispatchers.IO) {
            viewModel.loadMovies()
            assertLiveDataEquals(createMovieResponse.results, viewModel.movies)
            assertLiveDataEquals(true, viewModel.successRequest)
        }
    }

    @Test
    fun `Get a list of movies without successful`() = runBlocking {
        `when`(api.upcomingMovies(any(), any(), any(), any())).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "{error: not fund}")))
        val repository = TmdbRepository(api)
        val viewModel = HomeViewModel(repository)
        withContext(Dispatchers.IO) {
            viewModel.loadMovies()
            assertLiveDataEquals(true, viewModel.errorRequest)
            assertLiveDataNull(viewModel.movies)
        }
    }
}