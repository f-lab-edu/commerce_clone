package com.chan.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.chan.database.entity.order.OrderEntity

@Dao
interface OrdersDao {

    @Insert
    suspend fun insertOrder(order: OrderEntity)

    @Query("SELECT * FROM orders ORDER BY orderData DESC")
    fun getAllOrdersPaging(): PagingSource<Int, OrderEntity>
}