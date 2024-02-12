package com.aungbophyoe.domain.di

import com.aungbophyoe.data.remote.ApiService
import com.aungbophyoe.data.repo.NewsRepository
import com.aungbophyoe.domain.mapper.NetworkMapper
import com.aungbophyoe.domain.repo.NewsRepositoryImpl
import com.aungbophyoe.domain.usecase.NewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideNewsRepository(apiService: ApiService): NewsRepository {
        return NewsRepositoryImpl(apiService)
    }

    @Provides
    fun provideNewsUseCase(newsRepositoryImpl: NewsRepositoryImpl,networkMapper: NetworkMapper) : NewsUseCase {
        return NewsUseCase(newsRepositoryImpl,networkMapper)
    }
}