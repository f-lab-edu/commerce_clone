package com.chan.feature.ui.home.model

data class HomePopularItemModel(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val originPrice: String,
    val discountedPrice: String,
    val discountPercent: String,
    val rating: String
)