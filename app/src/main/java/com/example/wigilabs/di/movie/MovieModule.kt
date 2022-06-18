package com.example.wigilabs.di.movie

import com.example.data.remote.api.MovieApi
import com.example.data.repository.MovieRemoteRepositoryImpl
import com.example.domain.repository.DomainExceptionRepository
import com.example.domain.repository.MovieLocalRepository
import com.example.domain.repository.MovieRemoteRepository
import com.example.domain.uc.GetMovieUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object MovieModule {

    @Provides
    @ViewModelScoped
    fun getMovieUCProvider(
        movieLocalRepository: MovieLocalRepository,
        movieRemoteRepository: MovieRemoteRepository
    ): GetMovieUC = GetMovieUC(
        movieLocalRepository = movieLocalRepository,
        movieRemoteRepository = movieRemoteRepository
    )

    @Provides
    @ViewModelScoped
    fun movieRemoteRepositoryProvider(
        movieApi: MovieApi,
        domainExceptionRepository: DomainExceptionRepository
    ): MovieRemoteRepository = MovieRemoteRepositoryImpl(
        api = movieApi,
        exception = domainExceptionRepository
    )

    @Provides
    @ViewModelScoped
    fun movieApiProvider(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)
}
