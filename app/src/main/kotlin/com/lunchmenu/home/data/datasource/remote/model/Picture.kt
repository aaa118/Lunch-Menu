package com.lunchmenu.home.data.datasource.remote.model


import kotlinx.serialization.Serializable

@Serializable
data class Picture (
    val id: String,
    val url: String,
)
