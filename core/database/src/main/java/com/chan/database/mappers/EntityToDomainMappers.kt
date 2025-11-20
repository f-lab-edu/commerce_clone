package com.chan.database.mappers

import com.chan.database.entity.CommonProductEntity
import com.chan.database.entity.order.OrderEntity
import com.chan.domain.OrdersVO
import com.chan.domain.ProductsVO

fun CommonProductEntity.toProductsVO(): ProductsVO {
    return ProductsVO(
        productId = productId,
        productName = productName,
        brandName = brandName,
        imageUrl = imageUrl,
        originalPrice = originalPrice,
        discountPercent = discountPercent,
        discountPrice = discountPrice,
        tags = tags,
        reviewRating = reviewRating,
        reviewCount = reviewCount,
        categoryIds = categoryIds,
    )
}

fun OrderEntity.toOrdersVO() : OrdersVO {
    return OrdersVO(
        orderId = orderId,
        orderData = orderData,
        totalPrice = totalPrice,
        createdAt = createdAt,
        items = items.map { it.toOrderItems() }
    )
}

fun OrderEntity.OrderItem.toOrderItems() : OrdersVO.OrderItemVO {
    return OrdersVO.OrderItemVO(
        productId = productId,
        productName = productName,
        imageUrl = imageUrl,
        quantity = quantity,
        price = price
    )
}