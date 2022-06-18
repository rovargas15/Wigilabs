package com.example.data.remote.mapper

import com.example.data.remote.dto.MovieDto
import com.example.data.util.Mapper
import com.example.domain.model.Movie

val mapFromMovie: Mapper<MovieDto, Movie> = { input ->
    with(input) {
        Movie(
            id = id,
            adult = adult,
            backdropPath = backdropPath,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}
