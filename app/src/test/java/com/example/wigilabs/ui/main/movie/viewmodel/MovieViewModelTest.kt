package com.example.wigilabs.ui.main.movie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.exception.UnknownError
import com.example.domain.model.Movie
import com.example.domain.uc.GetMovieUC
import com.example.wigilabs.ui.main.movie.intent.MovieEvent
import com.example.wigilabs.ui.main.movie.state.MovieState
import com.example.wigilabs.util.DispatcherRule
import com.example.wigilabs.util.getOrAwaitValue
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MovieViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = DispatcherRule()

    private val getMovieUC: GetMovieUC = mockk()

    private lateinit var movieViewModel: MovieViewModel

    @Test
    fun giveListMovieWhereGetMovieThenSuccess() {
        // Give
        val movie: Movie = mockk()
        every { getMovieUC.invoke() } answers { flowOf(Result.success(listOf(movie))) }

        // When
        movieViewModel = MovieViewModel(
            getMovieUC = getMovieUC,
            coroutineDispatcher = mainDispatcherRule.testDispatcher
        )

        // Then
        val result = movieViewModel.viewState.getOrAwaitValue()
        assert(result is MovieState.Success)
        Assert.assertEquals((result as MovieState.Success).movies, listOf(movie))

        verify(exactly = 1) { getMovieUC.invoke() }
        confirmVerified(getMovieUC)
    }

    @Test
    fun giveListMovieWhereGetMovieALlThenSuccess() {
        // Give
        val movie: Movie = mockk()
        every { getMovieUC.invoke() } answers { flowOf(Result.success(listOf(movie))) }

        // When
        movieViewModel = MovieViewModel(
            getMovieUC = getMovieUC,
            coroutineDispatcher = mainDispatcherRule.testDispatcher
        )

        movieViewModel.process(MovieEvent.GetMovieALl)

        // Then
        val result = movieViewModel.viewState.getOrAwaitValue()
        assert(result is MovieState.Success)
        Assert.assertEquals((result as MovieState.Success).movies, listOf(movie))

        verify(exactly = 2) { getMovieUC.invoke() }
        confirmVerified(getMovieUC)
    }

    @Test
    fun giveListMovieWhereReloadThenSuccess() {
        // Give
        val movie: Movie = mockk()
        every { getMovieUC.invoke() } answers { flowOf(Result.success(listOf(movie))) }

        // When
        movieViewModel = MovieViewModel(
            getMovieUC = getMovieUC,
            coroutineDispatcher = mainDispatcherRule.testDispatcher
        )

        movieViewModel.process(MovieEvent.Reload)

        // Then
        val result = movieViewModel.viewState.getOrAwaitValue()
        assert(result is MovieState.Success)
        Assert.assertEquals((result as MovieState.Success).movies, listOf(movie))

        verify(exactly = 2) { getMovieUC.invoke() }
        confirmVerified(getMovieUC)
    }

    @Test
    fun giveListMovieWhereGetMovieThenFailure() {
        // Give
        every { getMovieUC.invoke() } answers { flowOf(Result.failure(UnknownError())) }

        // When
        movieViewModel = MovieViewModel(
            getMovieUC = getMovieUC,
            coroutineDispatcher = mainDispatcherRule.testDispatcher
        )

        // Then
        val result = movieViewModel.viewState.getOrAwaitValue()
        assert(result is MovieState.Error)

        verify(exactly = 1) { getMovieUC.invoke() }
        confirmVerified(getMovieUC)
    }
}
