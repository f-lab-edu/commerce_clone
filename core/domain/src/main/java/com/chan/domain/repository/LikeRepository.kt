package com.chan.domain.repository

import com.chan.domain.ProductsVO
import kotlinx.coroutines.flow.Flow


interface LikeRepository {
    fun getLikedProductIds() : Flow<Set<String>>
    suspend fun toggleLike(productId: String)
    fun getLikedProducts() : Flow<List<ProductsVO>>
}