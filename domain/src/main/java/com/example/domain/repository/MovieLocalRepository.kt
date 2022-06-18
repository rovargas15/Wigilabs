package com.example.domain.repository

import com.example.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalRepository {

    fun getMovieAll(): Flow<Result<List<Movie>>>

    fun getMovieById(movieId: Long): Flow<Result<Movie>>

    fun insertMovie(movies: List<Movie>): Flow<Result<Unit>>
}
