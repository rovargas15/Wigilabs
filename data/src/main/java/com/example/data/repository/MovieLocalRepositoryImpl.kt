package com.example.data.repository

import com.example.data.local.db.MovieDao
import com.example.data.local.entity.MovieEntity
import com.example.data.local.mapper.mapFromMovie
import com.example.data.local.mapper.mapFromMovieEntity
import com.example.data.util.Mapper
import com.example.domain.model.Movie
import com.example.domain.repository.MovieLocalRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieLocalRepositoryImpl(
    private val movieDao: MovieDao,
    private val mapperFromMovie: Mapper<MovieEntity, Movie> = mapFromMovie,
    private val mapperFromMovieEntity: Mapper<Movie, MovieEntity> = mapFromMovieEntity
) : MovieLocalRepository {

    override fun getMovieAll() = movieDao.getAll().map { result ->
        Result.success(result.map(mapperFromMovie))
    }

    override fun getMovieById(movieId: Long) = movieDao.getMovieById(movieId).map { result ->
        Result.success(mapperFromMovie(result))
    }

    override fun insertMovie(movies: List<Movie>) = flow {
        val resultMap = movies.map(mapperFromMovieEntity)
        emit(Result.success(movieDao.insertMovie(resultMap)))
    }
}
