package com.example.wigilabs.ui.main.movie.intent

sealed class MovieEvent {
    object GetMovieALl : MovieEvent()
    object Reload : MovieEvent()
}
