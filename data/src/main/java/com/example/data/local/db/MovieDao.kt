package com.example.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * from MovieEntity")
    fun getAll(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: List<MovieEntity>)

    @Query("SELECT * from MovieEntity where  id == :movieId")
    fun getMovieById(movieId: Long): Flow<MovieEntity>
}
