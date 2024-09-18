package com.lunchmenu.home.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LunchMenuItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)