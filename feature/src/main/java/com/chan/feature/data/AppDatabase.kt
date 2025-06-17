package com.chan.feature.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chan.feature.data.dao.HomeBannerDao
import com.chan.feature.data.dao.HomePopularItemDao
import com.chan.feature.data.entity.HomeBannerEntity
import com.chan.feature.data.entity.HomePopularItemEntity

@Database(entities = [HomeBannerEntity::class, HomePopularItemEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeBannerDao(): HomeBannerDao
    abstract fun homePopularItemDao() : HomePopularItemDao
}