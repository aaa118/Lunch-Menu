package com.lunchmenu.home.data.datasource.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class YelpResponse(val total: Int, val businesses: List<Businesses>)

@Entity
@Serializable
data class Businesses(
    val rating: Double,
    val price: String? = null,
    val name: String?,
    val phone: String?,
    @PrimaryKey
    val id: String,
    val distance: Double,
    val categories: List<Categories>,
    val image_url: String,
)

@Serializable
data class Categories (var alias: String, val title: String)