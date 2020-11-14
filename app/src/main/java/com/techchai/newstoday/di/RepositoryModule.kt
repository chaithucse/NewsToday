package com.techchai.newstoday.di

import com.techchai.newstoday.data.NewsRepository
import com.techchai.newstoday.data.io.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun providesRepository(apiService: ApiService): NewsRepository {
        return NewsRepository(apiService)
    }
}
