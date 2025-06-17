package com.chan.feature.domain.repository

import com.chan.feature.domain.vo.HomePopularItemVO

interface HomePopularItemRepository {
    suspend fun getPopularItemAll() : List<HomePopularItemVO>
}