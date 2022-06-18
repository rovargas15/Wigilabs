package com.example.wigilabs.ui.main.detail.intent

sealed class DetailMovieEvent {
    data class GetMovieById(val userId: Long) : DetailMovieEvent()
}
