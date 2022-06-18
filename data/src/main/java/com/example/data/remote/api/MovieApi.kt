package com.example.data.remote.api

import com.example.data.remote.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET(GET_MOVIE)
    suspend fun getMovies(
        @Query("api_key") key: String = "09963e300150f9831c46a1828a82a984",
        @Query("language") language: String = "en-US"
    ): MovieResponse
}

private const val GET_MOVIE = "movie/popular"
