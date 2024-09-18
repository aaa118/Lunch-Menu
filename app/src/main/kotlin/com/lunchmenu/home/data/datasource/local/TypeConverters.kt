package com.lunchmenu.home.data.datasource.local

import androidx.room.TypeConverter
import com.lunchmenu.home.data.datasource.remote.model.Businesses
import com.lunchmenu.home.data.datasource.remote.model.Categories
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TypeConverters {
    @TypeConverter
    fun fromStringToListOfBusinesses(value: String): List<Businesses?>? {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromBusinessesArrayListToString(list: List<Businesses?>?): String {
        return Json.encodeToString(list)
    }
    @TypeConverter
    fun fromStringToListOfCategories(value: String): List<Categories?>? {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromCategoriesArrayListToString(list: List<Categories?>?): String {
        return Json.encodeToString(list)
    }
}