package com.example.domain.uc

import com.example.domain.exception.UnknownError
import com.example.domain.model.Movie
import com.example.domain.repository.MovieLocalRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetMovieByIdUCTest {

    private val movieLocalRepository: MovieLocalRepository = mockk()
    private val getMovieByIdUC = GetMovieByIdUC(movieLocalRepository = movieLocalRepository)

    @Test
    fun giveAllWhereGetMovieAllThenSuccess() = runBlocking {
        // Give
        val movie: Movie = mockk()
        every { movieLocalRepository.getMovieById(1) } answers { flowOf(Result.success(movie)) }

        // Where
        getMovieByIdUC.invoke(1).collect {
            assert(it.isSuccess)
            Assert.assertEquals(movie, it.getOrNull())
        }

        // Then
        verify { movieLocalRepository.getMovieById(1) }
        confirmVerified(movieLocalRepository)
    }

    @Test
    fun giveExceptionWhereGetMovieAllThenFailure() = runBlocking {
        // Give
        every { movieLocalRepository.getMovieById(1) } answers {
            flowOf(Result.failure(UnknownError()))
        }

        // Where
        getMovieByIdUC.invoke(1).collect {
            assert(it.isFailure)
            Assert.assertEquals(UnknownError(), it.exceptionOrNull())
        }

        // Then
        verify { movieLocalRepository.getMovieById(1) }
        confirmVerified(movieLocalRepository)
    }
}
