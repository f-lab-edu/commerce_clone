package com.chan.mypage.ui

import com.chan.android.model.ProductsModel

data class OrdersModel(
    val orderId: String,
    val orderData: String,
    val totalPrice: Int,
    val createdAt: Long,
    val items: List<ProductsModel>,
)