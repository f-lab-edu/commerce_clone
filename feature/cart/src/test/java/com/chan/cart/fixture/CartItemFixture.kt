package com.chan.cart.fixture

import com.chan.cart.proto.CartItem

object CartItemFixture {
    fun createCarItems() = listOf(
        CartItem.newBuilder()
            .setProductId("p1")
            .setProductName("Test Product 1")
            .setOriginPrice(10000)
            .setDiscountedPrice(8000)
            .setQuantity(2)
            .setIsSelected(true)
            .build(),
        CartItem.newBuilder()
            .setProductId("p2")
            .setProductName("Test Product 2")
            .setOriginPrice(20000)
            .setDiscountedPrice(15000)
            .setQuantity(1)
            .setIsSelected(true)
            .build()
    )
}