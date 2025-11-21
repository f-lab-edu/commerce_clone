package com.chan.database.di

import com.chan.database.repository.LikeRepositoryImpl
import com.chan.domain.repository.LikeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindLikeRepository(
        likeRepositoryImpl: LikeRepositoryImpl
    ) : LikeRepository
}