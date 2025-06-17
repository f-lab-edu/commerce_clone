package com.chan.feature.domain.repository

import com.chan.feature.domain.vo.HomeBannerVO

interface HomeBannerRepository {
    suspend fun getBanners() : List<HomeBannerVO>
}