package com.lunchmenu.home.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lunchmenu.home.data.datasource.remote.model.Businesses
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessesDao {

    @Query("SELECT * FROM businesses  ORDER BY distance ASC")
    fun getAll(): Flow<List<Businesses>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(businesses: List<Businesses>)

}