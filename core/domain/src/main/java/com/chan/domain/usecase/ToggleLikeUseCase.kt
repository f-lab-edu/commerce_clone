package com.chan.domain.usecase

import com.chan.domain.repository.LikeRepository
import javax.inject.Inject

class ToggleLikeUseCase @Inject constructor(
    private val likeRepository: LikeRepository
) {
    suspend operator fun invoke(productId: String) {
        likeRepository.toggleLike(productId)
    }
}