package com.chan.domain.usecase

import com.chan.domain.repository.LikeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikedProductIdsUseCase @Inject constructor(
    private val likeRepository: LikeRepository,
) {
    operator fun invoke() : Flow<Set<String>> {
        return likeRepository.getLikedProductIds()
    }
}