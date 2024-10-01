package com.lunchmenu.home.data.datasource.remote

import com.lunchmenu.home.data.datasource.remote.model.Picture
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureService {

    @GET("list")
    suspend fun pictures(
        @Query("page") page: Int = 1,
        @Query("limit") perPage: Int = PER_PAGE_COUNT
    ): List<Picture>

    companion object {
        private const val PER_PAGE_COUNT = 30
    }
}
