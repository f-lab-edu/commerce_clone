package com.chan.database.repository

import android.content.Context
import androidx.datastore.core.DataStore
import com.chan.auth.domain.usecase.GetCurrentUserIdUseCase
import com.chan.database.dao.ProductsDao
import com.chan.database.datastore.LikeDataStoreManager
import com.chan.database.mappers.toProductsVO
import com.chan.domain.ProductsVO
import com.chan.domain.repository.LikeRepository
import com.chan.like.proto.Like
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject
import kotlin.collections.toMutableSet
import kotlin.collections.toSet

class LikeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val productsDao: ProductsDao
) : LikeRepository {

    private fun getLikeStore(): DataStore<Like> {
        val userId = getCurrentUserIdUseCase() ?: "guest"
        return LikeDataStoreManager.getDataStore(context, userId)
    }


    override fun getLikedProductIds(): Flow<Set<String>> {
        return getLikeStore().data
            .map { it.productIdsList.toSet() }
            .distinctUntilChanged()
    }

    override suspend fun toggleLike(productId: String) {
        getLikeStore().updateData { current ->
            val currentLikedSet = current.productIdsList.toMutableSet()

            if (currentLikedSet.contains(productId))
                currentLikedSet.remove(productId)
            else
                currentLikedSet.add(productId)

            current.toBuilder().clearProductIds().addAllProductIds(currentLikedSet).build()
        }
    }

    override fun getLikedProducts(): Flow<List<ProductsVO>> {
        return getLikeStore().data.mapLatest { likeProto ->
            val likedIds = likeProto.productIdsList.toSet()
            if (likedIds.isEmpty())
                return@mapLatest emptyList()

            val allProducts = productsDao.getAllProducts()

            allProducts
                .filter { it.productId in likedIds }
                .map { it.toProductsVO() }

        }
    }

}