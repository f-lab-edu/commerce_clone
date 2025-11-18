package com.chan.like.mapper

import com.chan.android.model.ProductsModel
import com.chan.domain.ProductsVO
import java.text.NumberFormat
import java.util.Locale

fun ProductsVO.toProductsModel(): ProductsModel {
    return ProductsModel(
        productId = productId,
        productName = productName,
        brandName = brandName,
        imageUrl = "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0016/A00000016559829ko.jpg?l=ko",
        originalPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(originalPrice) + "원",
        discountPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(discountPrice) + "원",
        discountPercent = "${discountPercent}%",
        tags = tags,
        reviewRating = reviewRating,
        reviewCount = "(${NumberFormat.getNumberInstance(Locale.KOREA).format(reviewCount)})",
        categoryIds = categoryIds,
    )
}