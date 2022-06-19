package com.app.wigilabs.di.db

import com.example.data.local.db.AppDatabase
import com.example.data.local.db.MovieDao
import com.example.wigilabs.di.db.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object DatabaseModuleTest {

    private val appDatabase = mockk<AppDatabase>(relaxed = true)
    private val userDao = mockk<MovieDao>(relaxed = true)

    @Provides
    @Singleton
    fun appDatabaseProvider() = appDatabase

    @Provides
    @Singleton
    fun userDaoProvider() = userDao
}
