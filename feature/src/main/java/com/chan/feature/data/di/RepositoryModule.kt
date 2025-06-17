package com.chan.feature.data.di

import com.chan.feature.data.repository.home.HomeBannerRepositoryImpl
import com.chan.feature.data.repository.home.HomePopularItemRepositoryImpl
import com.chan.feature.domain.repository.HomeBannerRepository
import com.chan.feature.domain.repository.HomePopularItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeBannerRepository(
        bannerRepositoryImpl: HomeBannerRepositoryImpl
    ) : HomeBannerRepository

    @Binds
    @Singleton
    abstract fun bindHomePopularItemRepository(
        bannerPopularItemRepositoryImpl: HomePopularItemRepositoryImpl
    ) : HomePopularItemRepository
}

