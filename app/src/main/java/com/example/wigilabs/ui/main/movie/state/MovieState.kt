package com.example.wigilabs.ui.main.movie.state

import com.example.domain.model.Movie

sealed interface MovieState {
    object Loader : MovieState
    object Error : MovieState
    data class Success(val movies: List<Movie>) : MovieState
}
