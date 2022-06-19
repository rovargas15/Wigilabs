package com.example.data.repository

import com.example.data.local.db.MovieDao
import com.example.data.local.entity.MovieEntity
import com.example.data.util.Mapper
import com.example.domain.model.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MovieLocalRepositoryImplTest {
    private val movieDao: MovieDao = mockk(relaxed = true)
    private val mapperFromMovie: Mapper<MovieEntity, Movie> = mockk(relaxed = true)
    private val mapperFromMovieEntity: Mapper<Movie, MovieEntity> = mockk(relaxed = true)

    private val movieLocalRepository = MovieLocalRepositoryImpl(
        movieDao = movieDao,
        mapperFromMovie = mapperFromMovie,
        mapperFromMovieEntity = mapperFromMovieEntity
    )

    @Test
    fun giveMoviesWhenGetMovieAllThenSuccess() = runBlocking {
        // Give
        val movieEntity: MovieEntity = mockk()
        val movie: Movie = mockk()
        coEvery { movieDao.getAll() } answers { flowOf(listOf(movieEntity)) }
        every { mapperFromMovie(movieEntity) } answers { movie }
        // When
        movieLocalRepository.getMovieAll().collect { result ->
            assert(result.isSuccess)
            Assert.assertEquals(result.getOrNull(), listOf(movie))
        }
        // Then
        coVerify(exactly = 1) { movieDao.getAll() }
        verify(exactly = 1) { mapperFromMovie(movieEntity) }
        confirmVerified(movieDao, mapperFromMovie)
    }

    @Test
    fun giveEmptyWhenGetMovieAllThenSuccess() = runBlocking {
        // Give
        coEvery { movieDao.getAll() } answers { flowOf(emptyList()) }

        // When
        movieLocalRepository.getMovieAll().collect { result ->
            assert(result.isSuccess)
            Assert.assertEquals(result.getOrNull(), emptyList<Movie>())
        }
        // Then
        coVerify(exactly = 1) { movieDao.getAll() }
        confirmVerified(movieDao)
    }

    @Test
    fun giveMovieWhenMovieByIdThenSuccess() = runBlocking {
        // Give
        val movieEntity: MovieEntity = mockk()
        val movie: Movie = mockk()
        coEvery { movieDao.getMovieById(1) } answers { flowOf(movieEntity) }
        every { mapperFromMovie(movieEntity) } answers { movie }
        // When
        movieLocalRepository.getMovieById(1).collect { result ->
            assert(result.isSuccess)
            Assert.assertEquals(result.getOrNull(), movie)
        }
        // Then
        coVerify(exactly = 1) { movieDao.getMovieById(1) }
        verify(exactly = 1) { mapperFromMovie(movieEntity) }
        confirmVerified(movieDao, mapperFromMovie)
    }

    @Test
    fun giveListAllWhenInsertMovieThenSuccess() = runBlocking {
        // Give
        val movieEntity: MovieEntity = mockk()
        val movie: Movie = mockk()
        coEvery { movieDao.insertMovie(listOf(movieEntity)) } returns Unit
        every { mapperFromMovieEntity(movie) } answers { movieEntity }

        // When
        movieLocalRepository.insertMovie(listOf(movie)).collect { result ->
            assert(result.isSuccess)
        }

        // Then
        coVerify(exactly = 1) { movieDao.insertMovie(listOf(movieEntity)) }
        verify(exactly = 1) { mapperFromMovieEntity(movie) }
        confirmVerified(movieDao, mapperFromMovie)
    }
}
