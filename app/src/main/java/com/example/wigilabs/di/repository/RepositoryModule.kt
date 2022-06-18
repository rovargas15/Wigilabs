package com.example.wigilabs.di.repository

import com.example.data.local.db.MovieDao
import com.example.data.repository.MovieLocalRepositoryImpl
import com.example.domain.repository.MovieLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun movieLocalRepositoryProvider(
        movieDao: MovieDao
    ): MovieLocalRepository = MovieLocalRepositoryImpl(
        movieDao = movieDao
    )
}
