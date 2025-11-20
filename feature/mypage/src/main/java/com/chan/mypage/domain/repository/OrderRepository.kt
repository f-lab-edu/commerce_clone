package com.chan.mypage.domain.repository

import androidx.paging.PagingData
import com.chan.domain.OrdersVO
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getAllOrdersPaging() : Flow<PagingData<OrdersVO>>
}