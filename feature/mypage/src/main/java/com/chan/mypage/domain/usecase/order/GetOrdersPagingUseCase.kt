package com.chan.mypage.domain.usecase.order

import androidx.paging.PagingData
import com.chan.domain.OrdersVO
import com.chan.mypage.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersPagingUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke() : Flow<PagingData<OrdersVO>> {
        return orderRepository.getAllOrdersPaging()
    }
}