package com.arctouch.codechallenge.view.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arctouch.codechallenge.model.createMovie
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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
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
    fun `Get details from a movie`() = runBlocking {
        Mockito.`when`(api.movie(any(), any(), any())).thenReturn(Response.success(createMovie))
        val repository = TmdbRepository(api)
        val viewModel = MovieViewModel(repository, 1)
        withContext(Dispatchers.IO) {
            viewModel.getMovieById()
            assertLiveDataEquals(createMovie, viewModel.movie)
            assertLiveDataEquals(true, viewModel.successRequest)
        }
    }

    @Test
    fun `Show error screen when network fails`() = runBlocking {
        Mockito.`when`(api.movie(any(), any(), any())).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "{error: not fund}")))
        val repository = TmdbRepository(api)
        val viewModel = MovieViewModel(repository, 1)
        withContext(Dispatchers.IO) {
            viewModel.getMovieById()
            assertLiveDataEquals(true, viewModel.errorRequest)
            assertLiveDataNull(viewModel.movie)
        }
    }
}