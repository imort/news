package com.github.imort.news.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SourceModule {
    @Provides
    @Singleton
    fun source(): Source = object : Source {
        override val country: String = "pt"
    }
}