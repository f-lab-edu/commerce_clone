package com.chan.database.entity.order

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey
    val orderId: String, //주문 번호
    val orderData: String,
    val totalPrice: Int,
    val createdAt: Long,
    val items: List<OrderItem>
) {
    data class OrderItem(
        val productId: String,
        val productName: String,
        val imageUrl: String,
        val quantity: Int,
        val price: Int
    )
}