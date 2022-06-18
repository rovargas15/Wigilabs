package com.example.domain.repository

import com.example.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRemoteRepository {

    fun getMovie(): Flow<Result<List<Movie>>>
}
