package com.chan.mypage.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.chan.database.mappers.toOrdersVO
import com.chan.domain.OrdersVO
import com.chan.mypage.data.datasource.OrderLocalDataSource
import com.chan.mypage.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderLocalDataSource: OrderLocalDataSource,
) : OrderRepository {
    override fun getAllOrdersPaging(): Flow<PagingData<OrdersVO>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { orderLocalDataSource.getOrderPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toOrdersVO() }
        }
    }
}