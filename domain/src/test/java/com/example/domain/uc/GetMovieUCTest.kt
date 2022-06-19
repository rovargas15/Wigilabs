package com.example.domain.uc

import com.example.domain.exception.UnknownError
import com.example.domain.model.Movie
import com.example.domain.repository.MovieLocalRepository
import com.example.domain.repository.MovieRemoteRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetMovieUCTest {

    private val movieLocalRepository: MovieLocalRepository = mockk()
    private val movieRemoteRepository: MovieRemoteRepository = mockk()

    private val getMovieUC = GetMovieUC(
        movieLocalRepository = movieLocalRepository,
        movieRemoteRepository = movieRemoteRepository
    )

    @Test
    fun giveMovieLocalWhereGetMovieAllThenSuccess() = runBlocking {
        // Give
        val movie: Movie = mockk()
        every { movieLocalRepository.getMovieAll() } answers { flowOf(Result.success(listOf(movie))) }

        // Where
        getMovieUC.invoke().collect {
            assert(it.isSuccess)
            Assert.assertEquals(listOf(movie), it.getOrNull())
        }

        // Then
        verify { movieLocalRepository.getMovieAll() }
    }

    @Test
    fun giveExceptionWhereGetMovieAllThenFailure() = runBlocking {
        // Give
        every { movieLocalRepository.getMovieAll() } answers { flowOf(Result.failure(UnknownError())) }

        // Where
        getMovieUC.invoke().collect {
            assert(it.isFailure)
            Assert.assertEquals(UnknownError(), it.exceptionOrNull())
        }

        // Then
        verify { movieLocalRepository.getMovieAll() }
        confirmVerified(movieLocalRepository)
    }

    @Test
    fun giveEmptyLocalWhereGetMovieAllThenSuccess() = runBlocking {
        // Give
        val movie: Movie = mockk()
        every { movieLocalRepository.getMovieAll() } answers { flowOf(Result.success(emptyList())) }
        every { movieLocalRepository.insertMovie(listOf(movie)) } answers {
            flowOf(
                Result.success(
                    Unit
                )
            )
        }
        every { movieRemoteRepository.getMovie() } answers { flowOf(Result.success(listOf(movie))) }

        // Where
        getMovieUC.invoke().collect {
            assert(it.isSuccess)
            Assert.assertEquals(listOf(movie), it.getOrNull())
        }

        // Then
        verify {
            movieLocalRepository.getMovieAll()
            movieLocalRepository.insertMovie(listOf(movie))
            movieRemoteRepository.getMovie()
        }
        confirmVerified(movieLocalRepository, movieRemoteRepository)
    }

    @Test
    fun giveExceptionWhereGetMovieThenFailure() = runBlocking {
        // Give
        every { movieLocalRepository.getMovieAll() } answers { flowOf(Result.success(emptyList())) }
        every { movieRemoteRepository.getMovie() } answers { flowOf(Result.failure(UnknownError())) }

        // Where
        getMovieUC.invoke().collect {
            assert(it.isFailure)
            Assert.assertEquals(UnknownError(), it.exceptionOrNull())
        }

        // Then
        verify {
            movieLocalRepository.getMovieAll()
            movieRemoteRepository.getMovie()
        }
        confirmVerified(movieLocalRepository, movieRemoteRepository)
    }

    @Test
    fun giveExceptionWhereInsertMovieThenFailure() = runBlocking {
        // Give
        val movie: Movie = mockk()
        every { movieLocalRepository.getMovieAll() } answers { flowOf(Result.success(emptyList())) }
        every { movieLocalRepository.insertMovie(listOf(movie)) } answers {
            flowOf(
                Result.failure(
                    UnknownError()
                )
            )
        }
        every { movieRemoteRepository.getMovie() } answers { flowOf(Result.success(listOf(movie))) }

        // Where
        getMovieUC.invoke().collect {
            assert(it.isFailure)
            Assert.assertEquals(UnknownError(), it.exceptionOrNull())
        }

        // Then
        verify {
            movieLocalRepository.getMovieAll()
            movieLocalRepository.insertMovie(listOf(movie))
            movieRemoteRepository.getMovie()
        }
        confirmVerified(movieLocalRepository, movieRemoteRepository)
    }
}
