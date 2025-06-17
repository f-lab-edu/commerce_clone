package com.chan.feature.data.di

import android.content.Context
import androidx.room.Room
import com.chan.feature.data.AppDatabase
import com.chan.feature.data.dao.HomeBannerDao
import com.chan.feature.data.dao.HomePopularItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "commerce_clone_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHomeBannerDao(db: AppDatabase): HomeBannerDao = db.homeBannerDao()

    @Provides
    fun provideHomePopularItemDao(db: AppDatabase): HomePopularItemDao = db.homePopularItemDao()
}