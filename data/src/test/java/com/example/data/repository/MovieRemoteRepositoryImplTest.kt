package com.example.data.repository

import com.example.data.remote.api.MovieApi
import com.example.data.remote.dto.MovieDto
import com.example.data.remote.dto.MovieResponseDto
import com.example.data.util.Mapper
import com.example.domain.exception.BadRequestException
import com.example.domain.model.Movie
import com.example.domain.repository.DomainExceptionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MovieRemoteRepositoryImplTest {
    private val api: MovieApi = mockk()
    private val mapper: Mapper<MovieDto, Movie> = mockk()
    private val exception: DomainExceptionRepository = mockk()
    private val movieRemoteRepository = MovieRemoteRepositoryImpl(
        api = api,
        mapper = mapper,
        exception = exception
    )

    @Test
    fun giveMoviesWhenGetMovieAllThenSuccess() = runBlocking {
        // Give
        val movieDto: MovieDto = mockk()
        val movie: Movie = mockk()
        val responseDto: MovieResponseDto = mockk {
            every { results } returns listOf(movieDto)
        }

        coEvery {
            api.getMovies(
                "09963e300150f9831c46a1828a82a984",
                "en-US"
            )
        } answers { responseDto }
        every { mapper(movieDto) } returns movie

        // When
        movieRemoteRepository.getMovie().collect { result ->
            assert(result.isSuccess)
            Assert.assertEquals(result.getOrNull(), listOf(movie))
        }

        // Then
        coVerify(exactly = 1) { api.getMovies("09963e300150f9831c46a1828a82a984", "en-US") }
        verify(exactly = 1) { mapper(movieDto) }
        confirmVerified(api, mapper)
    }

    @Test
    fun giveExceptionWhenGetMovieAllThenFailure() = runBlocking {
        // Give
        val error = Throwable()
        every { exception.manageError(error) } returns BadRequestException
        coEvery { api.getMovies("09963e300150f9831c46a1828a82a984", "en-US") } throws error

        // When
        movieRemoteRepository.getMovie().collect { result ->
            assert(result.isFailure)
            Assert.assertEquals(result.exceptionOrNull(), BadRequestException)
        }

        // Then
        coVerify(exactly = 1) { api.getMovies("09963e300150f9831c46a1828a82a984", "en-US") }
        verify(exactly = 1) { exception.manageError(error) }
        confirmVerified(api, exception)
    }
}
