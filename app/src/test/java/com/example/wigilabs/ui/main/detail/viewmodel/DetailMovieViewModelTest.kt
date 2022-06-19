package com.example.wigilabs.ui.main.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.domain.exception.UnknownError
import com.example.domain.model.Movie
import com.example.domain.uc.GetMovieByIdUC
import com.example.wigilabs.ui.main.detail.state.DetailMovieState
import com.example.wigilabs.ui.main.navigation.Parameter
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

class DetailMovieViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = DispatcherRule()

    private val getMovieByIdUC: GetMovieByIdUC = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private lateinit var detailMovieViewModel: DetailMovieViewModel

    @Test
    fun giveMovieWhereGetMovieThenSuccess() {
        // Give
        val movie: Movie = mockk()
        every { savedStateHandle.get<Long>(Parameter.MOVIE_ID) } returns 1
        every { getMovieByIdUC.invoke(1) } answers { flowOf(Result.success(movie)) }

        // When
        detailMovieViewModel = DetailMovieViewModel(
            getMovieByIdUC = getMovieByIdUC,
            savedStateHandle = savedStateHandle,
            coroutineDispatcher = mainDispatcherRule.testDispatcher
        )
        val result = detailMovieViewModel.viewState.getOrAwaitValue()

        // Then
        assert(result is DetailMovieState.Success)
        Assert.assertEquals((result as DetailMovieState.Success).movie, movie)
        verify(exactly = 1) {
            getMovieByIdUC.invoke(1)
            savedStateHandle.get<Long>(Parameter.MOVIE_ID)
        }
        confirmVerified(getMovieByIdUC, savedStateHandle)
    }

    @Test
    fun giveMovieWhereGetMovieThenFailure() {
        // Give
        every { getMovieByIdUC.invoke(1) } answers { flowOf(Result.failure(UnknownError())) }
        every { savedStateHandle.get<Long>(Parameter.MOVIE_ID) } returns 1

        // When
        detailMovieViewModel = DetailMovieViewModel(
            getMovieByIdUC = getMovieByIdUC,
            savedStateHandle = savedStateHandle,
            coroutineDispatcher = mainDispatcherRule.testDispatcher
        )
        val result = detailMovieViewModel.viewState.getOrAwaitValue()

        // Then
        assert(result is DetailMovieState.Error)
        verify(exactly = 1) {
            getMovieByIdUC.invoke(1)
            savedStateHandle.get<Long>(Parameter.MOVIE_ID)
        }
        confirmVerified(getMovieByIdUC, savedStateHandle)
    }
}
