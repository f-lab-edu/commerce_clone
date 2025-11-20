package com.chan.mypage.data.di

import com.chan.mypage.data.repository.OrderRepositoryImpl
import com.chan.mypage.domain.repository.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl,
    ): OrderRepository
}