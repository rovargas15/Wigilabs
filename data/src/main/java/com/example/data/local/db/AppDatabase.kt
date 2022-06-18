package com.example.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
