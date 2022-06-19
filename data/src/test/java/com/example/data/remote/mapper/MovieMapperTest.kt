package com.example.data.remote.mapper

import com.example.data.remote.dto.MovieDto
import org.junit.Assert
import org.junit.Test

class MovieMapperTest {

    @Test
    fun mapFromMovieEntityTest() {
        val movieDto = MovieDto(
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

        val result = mapFromMovie(movieDto)
        Assert.assertEquals(result.id, 1)
        Assert.assertEquals(result.adult, false)
        Assert.assertEquals(result.backdropPath, "backdropPath")
        Assert.assertEquals(result.originalLanguage, "originalLanguage")
        Assert.assertEquals(result.originalTitle, "originalTitle")
        Assert.assertEquals(result.overview, "overview")
        assert(result.popularity == 5.0)
        Assert.assertEquals(result.posterPath, "posterPath")
        Assert.assertEquals(result.releaseDate, "releaseDate")
        Assert.assertEquals(result.title, "title")
        Assert.assertEquals(result.video, false)
        assert(result.voteAverage == 9.5)
        Assert.assertEquals(result.voteCount, 12345)
    }
}
