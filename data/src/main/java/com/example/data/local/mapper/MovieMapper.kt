package com.example.data.local.mapper

import com.example.data.local.entity.MovieEntity
import com.example.data.util.Mapper
import com.example.domain.model.Movie

val mapFromMovie: Mapper<MovieEntity, Movie> = { input ->
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

val mapFromMovieEntity: Mapper<Movie, MovieEntity> = { input ->
    with(input) {
        MovieEntity(
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
