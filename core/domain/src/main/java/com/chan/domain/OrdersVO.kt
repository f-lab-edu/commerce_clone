package com.chan.domain

data class OrdersVO(
    val orderId: String,
    val orderData: String,
    val totalPrice: Int,
    val createdAt: Long,
    val items: List<OrderItemVO>
) {
    data class OrderItemVO(
        val productId: String,
        val productName: String,
        val imageUrl: String,
        val quantity: Int,
        val price: Int,
    )
}