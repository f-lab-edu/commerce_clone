package com.chan.feature.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.feature.data.entity.HomeBannerEntity

@Dao
interface HomeBannerDao {

    @Query("SELECT * FROM homeBanner")
    suspend fun getBannerAll() : List<HomeBannerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(banners: List<HomeBannerEntity>)

    @Query("DELETE FROM homeBanner")
    suspend fun deleteAll()
}