package com.jonathansteele.feature.rosters

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RosterModule {
    @Provides
    @Singleton
    fun provideRostersFetcher(okHttpClient: OkHttpClient): RostersFetcher {
        return RostersFetcher(okHttpClient)
    }
}