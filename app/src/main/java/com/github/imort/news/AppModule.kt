package com.github.imort.news

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface AppModule {
    @Binds
    @Singleton
    fun dispatchers(impl: AppDispatchersImpl): AppDispatchers
}