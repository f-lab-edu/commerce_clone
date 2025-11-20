package com.chan.mypage.data.datasource

import androidx.paging.PagingSource
import com.chan.database.dao.OrdersDao
import com.chan.database.entity.order.OrderEntity
import javax.inject.Inject

class OrderLocalDataSource @Inject constructor(
    private val ordersDao: OrdersDao,
) {
    fun getOrderPagingSource(): PagingSource<Int, OrderEntity> = ordersDao.getAllOrdersPaging()
}