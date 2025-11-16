package com.chan.home.mapper

import com.chan.android.model.ProductsModel
import com.chan.domain.ProductsVO
import com.chan.home.domain.vo.ProductVO
import com.chan.home.domain.vo.RankingCategoryTabVO
import com.chan.home.model.HomePopularItemModel
import com.chan.home.model.HomeRankingCategoryTabModel
import com.chan.home.model.HomeSaleProductModel
import java.text.NumberFormat
import java.util.Locale

fun ProductVO.toPopularItemModel(): HomePopularItemModel {
    return HomePopularItemModel(
        productId = productId,
        productName = productName,
        brandName = brandName,
        imageUrl = "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0016/A00000016559829ko.jpg?l=ko",
        originalPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(originalPrice) + "원",
        discountPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(discountPrice) + "원",
        discountPercent = "${discountPercent}%",
        tags = tags,
        reviewRating = reviewRating.toString(),
        reviewCount = "(${NumberFormat.getNumberInstance(Locale.KOREA).format(reviewCount)})"
    )
}

fun ProductsVO.toProductsModel(): ProductsModel {
    return ProductsModel(
        productId = productId,
        productName = productName,
        brandName = brandName,
        imageUrl = getRandomImageUrl(productId),
        originalPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(originalPrice) + "원",
        discountPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(discountPrice) + "원",
        discountPercent = "${discountPercent}%",
        tags = tags,
        reviewRating = reviewRating,
        reviewCount = "(${NumberFormat.getNumberInstance(Locale.KOREA).format(reviewCount)})",
        categoryIds = categoryIds,
    )
}


fun RankingCategoryTabVO.toRankingCategoryTabsModel(): HomeRankingCategoryTabModel {
    return HomeRankingCategoryTabModel(
        id = id,
        name = name
    )
}

fun ProductVO.toSaleProductModel(): HomeSaleProductModel {
    return HomeSaleProductModel(
        productId = productId,
        productName = productName,
        brandName = brandName,
        imageUrl = getRandomImageUrl(productId),
        originalPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(originalPrice) + "원",
        discountPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(discountPrice) + "원",
        discountPercent = "${discountPercent}%",
        tags = tags,
        reviewRating = reviewRating.toString(),
        reviewCount = "(${NumberFormat.getNumberInstance(Locale.KOREA).format(reviewCount)})"
    )
}

private fun getRandomImageUrl(seed: String): String {
    return "https://picsum.photos/seed/$seed/300/300"
}