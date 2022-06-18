package com.example.domain.uc

import com.example.domain.model.Movie
import com.example.domain.repository.MovieLocalRepository
import com.example.domain.repository.MovieRemoteRepository
import kotlinx.coroutines.flow.flow

class GetMovieUC(
    private val movieLocalRepository: MovieLocalRepository,
    private val movieRemoteRepository: MovieRemoteRepository
) {

    fun invoke() = flow<Result<List<Movie>>> {
        movieLocalRepository.getMovieAll().collect { resultLocal ->
            resultLocal.fold(
                onSuccess = { listMovieLocal ->
                    if (listMovieLocal.isEmpty()) {
                        movieRemoteRepository.getMovie()
                            .collect { resultRemote ->
                                resultRemote.fold(
                                    onSuccess = { listMovie ->
                                        movieLocalRepository.insertMovie(listMovie)
                                            .collect { resultInsert ->
                                                resultInsert.fold(
                                                    onSuccess = {
                                                        emit(Result.success(listMovie))
                                                    },
                                                    onFailure = { throwable ->
                                                        emit(Result.failure(throwable))
                                                    }
                                                )
                                            }
                                    },
                                    onFailure = { error ->
                                        emit(Result.failure(error))
                                    }
                                )
                            }
                    } else {
                        emit(Result.success(listMovieLocal))
                    }
                },
                onFailure = { error ->
                    emit(Result.failure(error))
                }
            )
        }
    }
}
