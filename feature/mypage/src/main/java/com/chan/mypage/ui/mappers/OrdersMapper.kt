package com.chan.mypage.ui.mappers

import com.chan.android.model.ProductsModel
import com.chan.domain.OrdersVO
import com.chan.mypage.ui.OrdersModel
import java.text.NumberFormat
import java.util.Locale

fun OrdersVO.toOrdersModel(): OrdersModel {
    return OrdersModel(
        orderId = orderId,
        orderData = orderData,
        totalPrice = totalPrice,
        createdAt = createdAt,
        items = items.map { it.toOrderItemModel() }
    )
}

fun OrdersVO.OrderItemVO.toOrderItemModel(): ProductsModel {
    return ProductsModel(
        productId = productId,
        productName = productName,
        brandName = null,
        imageUrl = "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0016/A00000016559829ko.jpg?l=ko",
        originalPrice = null,
        discountPercent = null,
        discountPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(price) + "원",
        tags = emptyList(),
        reviewRating = null,
        reviewCount = null,
        categoryIds = emptyList(),
        quantity = quantity
    )
}