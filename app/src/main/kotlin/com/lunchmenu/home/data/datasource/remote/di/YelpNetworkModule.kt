package com.lunchmenu.home.data.datasource.remote.di

import com.lunchmenu.home.data.datasource.remote.YelpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object YelpNetworkModule {

    @Singleton
    @Provides
    fun provideYelpApi(retrofit: Retrofit): YelpApi = retrofit.create()
}