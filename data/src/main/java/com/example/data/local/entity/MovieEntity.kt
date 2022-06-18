package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Long,
    val adult: Boolean,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
