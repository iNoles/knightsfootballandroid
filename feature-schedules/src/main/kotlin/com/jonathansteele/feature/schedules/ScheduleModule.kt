package com.jonathansteele.feature.schedules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScheduleModule {
    @Provides
    @Singleton
    fun provideScheduleFetcher(okHttpClient: OkHttpClient): ScheduleFetcher {
        return ScheduleFetcher(okHttpClient)
    }
}