package com.example.data.repository

import com.example.data.remote.api.MovieApi
import com.example.data.remote.dto.MovieDto
import com.example.data.remote.mapper.mapFromMovie
import com.example.data.util.Mapper
import com.example.domain.model.Movie
import com.example.domain.repository.DomainExceptionRepository
import com.example.domain.repository.MovieRemoteRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MovieRemoteRepositoryImpl(
    private val api: MovieApi,
    private val mapper: Mapper<MovieDto, Movie> = mapFromMovie,
    private val exception: DomainExceptionRepository
) : MovieRemoteRepository {

    override fun getMovie() = flow {
        val response: List<MovieDto> = api.getMovies().results
        emit(Result.success(response.map(mapper)))
    }.catch { error ->
        emit(Result.failure(exception.manageError(error)))
    }
}
