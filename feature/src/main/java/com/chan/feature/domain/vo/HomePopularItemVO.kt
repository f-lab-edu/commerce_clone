package com.chan.feature.domain.vo

data class HomePopularItemVO (
    val id: Int,
    val imageUrl: String,
    val name: String,
    val originPrice: String,
    val discountedPrice: String,
    val discountPercent: String,
    val rating: String
)
