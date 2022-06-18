package com.example.wigilabs.di.db

import android.content.Context
import androidx.room.Room
import com.example.data.local.db.AppDatabase
import com.example.data.local.db.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun userDaoProvider(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }
}

private const val DB_NAME = "movieDB"
