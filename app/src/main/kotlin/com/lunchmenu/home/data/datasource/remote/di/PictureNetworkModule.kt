package com.lunchmenu.home.data.datasource.remote.di

import com.lunchmenu.home.data.datasource.remote.PictureService
import com.lunchmenu.home.data.datasource.remote.YelpApi
import com.lunchmenu.platform.di.Picsum
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PictureNetworkModule {

    @Singleton
    @Provides
    fun providePictureService(@Picsum retrofit: Retrofit): PictureService = retrofit.create()
}