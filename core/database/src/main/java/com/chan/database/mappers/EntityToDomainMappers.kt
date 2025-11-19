package com.chan.database.mappers

import com.chan.database.entity.CommonProductEntity
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