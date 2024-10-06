package com.debanshudatta.data.di

import com.debanshudatta.data.repository.MovieDataRepository
import com.debanshudatta.data.repository.RemoteMovieDataRepository
import com.debanshudatta.network.domain.usecase.ClientWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideMovieDataRepository(clientWrapper: ClientWrapper): MovieDataRepository {
        return RemoteMovieDataRepository(clientWrapper)
    }
}