package com.chan.feature.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chan.feature.domain.vo.HomePopularItemVO

@Entity(tableName ="homePopularItem")
data class HomePopularItemEntity(
    @PrimaryKey
    val id: Int,
    val imageUrl: String,
    val name: String,
    val originPrice: String,
    val discountedPrice: String,
    val discountPercent: String,
    val rating: String
)

fun HomePopularItemEntity.toDomain() : HomePopularItemVO {
    return HomePopularItemVO(
        id = id,
        imageUrl = imageUrl,
        name = name,
        originPrice = originPrice,
        discountedPrice = discountedPrice,
        discountPercent = discountPercent,
        rating = rating
    )
}