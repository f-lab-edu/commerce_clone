package com.chan.feature.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.feature.data.entity.HomePopularItemEntity

@Dao
interface HomePopularItemDao {
    @Query("SELECT * FROM homePopularItem")
    suspend fun getPopularItemAll(): List<HomePopularItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularItem(popularItems: List<HomePopularItemEntity>)

    @Query("DELETE FROM homePopularItem")
    suspend fun deleteAll()
}