package com.lunchmenu.home.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lunchmenu.home.data.datasource.local.dao.BusinessesDao
import com.lunchmenu.home.data.datasource.local.dao.LunchMenuDao
import com.lunchmenu.home.data.datasource.local.entity.LunchMenuItem
import com.lunchmenu.home.data.datasource.remote.model.Businesses

@Database(entities = [Businesses::class, LunchMenuItem::class], version = 1, exportSchema = false)
@androidx.room.TypeConverters(TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun businessesDao(): BusinessesDao
    abstract fun lunchMenuDao(): LunchMenuDao
}