package com.chan.feature.data.repository.home

import com.chan.feature.data.dao.HomeBannerDao
import com.chan.feature.data.datasource.home.HomeBannerDataSource
import com.chan.feature.data.entity.toDomain
import com.chan.feature.domain.repository.HomeBannerRepository
import com.chan.feature.domain.vo.HomeBannerVO
import javax.inject.Inject

class HomeBannerRepositoryImpl @Inject constructor(
    private val homeBannerDao: HomeBannerDao,
    private val homeBannerDataSource: HomeBannerDataSource
) : HomeBannerRepository {
    override suspend fun getBanners(): List<HomeBannerVO> {
        homeBannerDao.deleteAll()
        if(homeBannerDao.getBannerAll().isEmpty()) {
            val dummy = homeBannerDataSource.getDummyHomeBanners()
            homeBannerDao.insertAll(dummy)
        }
        return homeBannerDao.getBannerAll().map { it.toDomain() }
    }
}