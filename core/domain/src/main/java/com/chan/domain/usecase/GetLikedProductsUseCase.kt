package com.chan.domain.usecase

import com.chan.domain.ProductsVO
import com.chan.domain.repository.LikeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikedProductsUseCase @Inject constructor(
    private val likeRepository: LikeRepository
) {
    operator fun invoke() : Flow<List<ProductsVO>> {
        return likeRepository.getLikedProducts()
    }
}