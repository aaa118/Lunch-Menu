package com.lunchmenu.home.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.lunchmenu.home.data.datasource.local.entity.LunchMenuItem
import kotlinx.coroutines.flow.Flow


@Dao
interface LunchMenuDao {

    @Query("SELECT * FROM lunchmenuitem")
    fun getAllFlowable(): Flow<List<LunchMenuItem>>

    @Query("SELECT * FROM lunchmenuitem")
    suspend fun getAll(): List<LunchMenuItem>

    @Insert
    suspend fun insertAll(items: List<LunchMenuItem>)

    @Upsert
    suspend fun insert(item: LunchMenuItem)
}