package com.lunchmenu.home.data.datasource.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lunchmenu.home.data.datasource.remote.model.Businesses
import com.lunchmenu.home.data.datasource.remote.model.Categories
import java.lang.reflect.Type

class TypeConverters {
    @TypeConverter
    fun fromStringToListOfBusinesses(value: String?): List<Businesses?>? {
        val listType: Type = object : TypeToken<List<Businesses?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromBusinessesArrayListToString(list: List<Businesses?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
    @TypeConverter
    fun fromStringToListOfCategories(value: String?): List<Categories?>? {
        val listType: Type = object : TypeToken<List<Categories?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromCategoriesArrayListToString(list: List<Categories?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}