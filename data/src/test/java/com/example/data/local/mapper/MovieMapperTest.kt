package com.example.data.local.mapper

import com.example.data.local.entity.MovieEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieMapperTest {

    @Test
    fun mapFromMovieEntityTest() {
        val movieEntity = MovieEntity(
            id = 1,
            adult = false,
            backdropPath = "backdropPath",
            originalLanguage = "originalLanguage",
            originalTitle = "originalTitle",
            overview = "overview",
            popularity = 5.0,
            posterPath = "posterPath",
            releaseDate = "releaseDate",
            title = "title",
            video = false,
            voteAverage = 9.5,
            voteCount = 12345
        )

        val result = mapFromMovie(movieEntity)
        assertEquals(result.id, 1)
        assertEquals(result.adult, false)
        assertEquals(result.backdropPath, "backdropPath")
        assertEquals(result.originalLanguage, "originalLanguage")
        assertEquals(result.originalTitle, "originalTitle")
        assertEquals(result.overview, "overview")
        assert(result.popularity == 5.0)
        assertEquals(result.posterPath, "posterPath")
        assertEquals(result.releaseDate, "releaseDate")
        assertEquals(result.title, "title")
        assertEquals(result.video, false)
        assert(result.voteAverage == 9.5)
        assertEquals(result.voteCount, 12345)
    }
}
