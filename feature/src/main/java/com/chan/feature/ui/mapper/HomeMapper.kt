package com.chan.feature.ui.mapper

import com.chan.feature.domain.vo.HomeBannerVO
import com.chan.feature.domain.vo.HomePopularItemVO
import com.chan.feature.ui.home.model.HomeBannerModel
import com.chan.feature.ui.home.model.HomePopularItemModel

fun HomeBannerVO.toPresentation() : HomeBannerModel {
    return HomeBannerModel(
        id = id,
        imageUrl = imageUrl,
        title = title
    )
}

fun HomePopularItemVO.toPresentation() : HomePopularItemModel {
    return HomePopularItemModel(
        id = id,
        imageUrl = imageUrl,
        name = name,
        originPrice = originPrice,
        discountedPrice = discountedPrice,
        discountPercent = discountPercent,
        rating = rating
    )
}