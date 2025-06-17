package com.chan.feature.data.repository.home

import com.chan.feature.data.dao.HomePopularItemDao
import com.chan.feature.data.datasource.home.HomePopularItemDataSource
import com.chan.feature.data.entity.toDomain
import com.chan.feature.domain.repository.HomePopularItemRepository
import com.chan.feature.domain.vo.HomePopularItemVO
import javax.inject.Inject

class HomePopularItemRepositoryImpl @Inject constructor(
    private val homePopularItemDao: HomePopularItemDao,
    private val dataSource: HomePopularItemDataSource
) : HomePopularItemRepository {
    override suspend fun getPopularItemAll(): List<HomePopularItemVO> {
        homePopularItemDao.deleteAll()
        if (homePopularItemDao.getPopularItemAll().isEmpty()) {
            val dummy = dataSource.getDummyPopularItems()
            homePopularItemDao.insertPopularItem(dummy)
        }
        return homePopularItemDao.getPopularItemAll().map { it.toDomain() }
    }
}