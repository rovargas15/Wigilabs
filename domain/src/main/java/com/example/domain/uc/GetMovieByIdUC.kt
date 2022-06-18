package com.example.domain.uc

import com.example.domain.repository.MovieLocalRepository

class GetMovieByIdUC(
    private val movieLocalRepository: MovieLocalRepository
) {

    fun invoke(movieId: Long) = movieLocalRepository.getMovieById(movieId = movieId)
}
