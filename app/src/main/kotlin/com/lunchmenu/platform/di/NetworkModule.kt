package com.lunchmenu.platform.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import javax.inject.Singleton
import okhttp3.MediaType.Companion.toMediaType
import javax.inject.Qualifier


private const val BASE_URL = "https://api.yelp.com/"
private const val BASE_URL2 = "https://picsum.photos/v2/"

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class Picsum

//https://unsplash.com/photos/LF8gK8-HGSg

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BASE_URL)
            .build()
    }

    @Picsum
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit2(): Retrofit {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BASE_URL2)
            .build()
    }
}
