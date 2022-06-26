package com.jonathansteele.feature.headlines

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeadlinesModule {
    @Provides
    @Singleton
    fun provideHeadlinesFetcher(okHttpClient: OkHttpClient): HeadlinesFetcher {
        return HeadlinesFetcher(okHttpClient)
    }
}