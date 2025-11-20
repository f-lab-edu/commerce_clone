package com.chan.android.model

data class ProductsModel(
    val productId: String,
    val productName: String,
    val brandName: String?,
    val imageUrl: String,
    val originalPrice: String?,
    val discountPercent: String?,
    val discountPrice: String?,
    val tags: List<String> = emptyList(),
    val reviewRating: Float?,
    val reviewCount: String?,
    val categoryIds: List<String> = emptyList(),
    val quantity: Int? = null
)