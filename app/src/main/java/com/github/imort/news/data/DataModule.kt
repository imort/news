package com.github.imort.news.data

import com.github.imort.news.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import javax.inject.Singleton

@Module(includes = [DataModule.Bindings::class])
@InstallIn(SingletonComponent::class)
internal object DataModule {
    @Provides
    @Singleton
    fun moshi(): Moshi =
        Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()

    @Provides
    @Singleton
    fun okHttpClient(endpoint: Endpoint): OkHttpClient =
        OkHttpClient.Builder()
            .addAuthInterceptor(endpoint.apiKey)
            .maybeAddLoggingInterceptor()
            .build()

    private fun OkHttpClient.Builder.addAuthInterceptor(apiKey: String): OkHttpClient.Builder =
        addInterceptor { chain ->
            val modifiedRequest = chain.request().newBuilder()
                .header("X-Api-Key", apiKey)
                .build()
            chain.proceed(modifiedRequest)
        }

    private fun OkHttpClient.Builder.maybeAddLoggingInterceptor(): OkHttpClient.Builder = apply {
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.HEADERS)
            }
            addInterceptor(logging)
        }
    }

    @Provides
    @Singleton
    fun retrofit(client: OkHttpClient, moshi: Moshi, endpoint: Endpoint): Retrofit =
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(endpoint.baseUrl)
            .build()

    @Provides
    @Singleton
    fun newsService(retrofit: Retrofit): NewsService =
        retrofit.create(NewsService::class.java)

    @Module
    @DisableInstallInCheck
    interface Bindings {
        @Binds
        @Singleton
        fun endpoint(endpoint: NewsEndpoint): Endpoint

        @Binds
        @Singleton
        fun storage(storage: NewsStorageImpl): NewsStorage
    }
}