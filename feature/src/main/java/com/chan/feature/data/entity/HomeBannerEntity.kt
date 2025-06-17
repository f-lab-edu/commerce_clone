package com.chan.feature.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chan.feature.domain.vo.HomeBannerVO

@Entity(tableName ="homeBanner")
data class HomeBannerEntity(
    @PrimaryKey val id: Int,
    val imageUrl: String,
    val title: String
)

fun HomeBannerEntity.toDomain() : HomeBannerVO {
    return HomeBannerVO(
        id = id,
        imageUrl = imageUrl,
        title = title
    )
}
