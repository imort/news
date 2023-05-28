package com.github.imort.news.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal interface RepositoryModule {
    @Binds
    @ViewModelScoped
    fun newsRepository(repo: NewsRepositoryImpl): NewsRepository
}