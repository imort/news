package com.github.imort.news.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {
    @Provides
    @Singleton
    fun moshi(): Moshi =
        Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()

    @Provides
    fun endpoint(): Endpoint =
        NewsEndpoint

    @Provides
    @Singleton
    fun okHttpClient(endpoint: Endpoint): OkHttpClient =
        OkHttpClient.Builder()
            .addAuthInterceptor(endpoint.apiKey)
            .build()

    private fun OkHttpClient.Builder.addAuthInterceptor(apiKey: String): OkHttpClient.Builder =
        addInterceptor { chain ->
            val modifiedRequest = chain.request().newBuilder()
                .header("X-Api-Key", apiKey)
                .build()
            chain.proceed(modifiedRequest)
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
}