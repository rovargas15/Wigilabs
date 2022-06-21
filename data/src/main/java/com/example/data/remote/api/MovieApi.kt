package com.example.data.remote.api

import com.example.data.BuildConfig
import com.example.data.remote.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET(GET_MOVIE)
    suspend fun getMovies(
        @Query("api_key") key: String = BuildConfig.KEY,
        @Query("language") language: String = LANGUAGE
    ): MovieResponseDto
}

private const val GET_MOVIE = "movie/popular"
private const val LANGUAGE = "en-US"
