package com.example.wigilabs.di.detail

import com.example.domain.repository.MovieLocalRepository
import com.example.domain.uc.GetMovieByIdUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DetailModule {

    @Provides
    @ViewModelScoped
    fun getMovieByIdUCProvider(
        movieLocalRepository: MovieLocalRepository
    ): GetMovieByIdUC = GetMovieByIdUC(movieLocalRepository = movieLocalRepository)
}
