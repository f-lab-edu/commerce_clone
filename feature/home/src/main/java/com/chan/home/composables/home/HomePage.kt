package com.chan.home.composables.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chan.android.ui.theme.Spacing
import com.chan.home.composables.home.banner.HomeBanner
import com.chan.home.home.HomeContract

@Composable
fun HomePage(
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit
) {
    val homeCategoryRankingPagerState = rememberPagerState { state.rankingCategoryTabs.size }

    LazyColumn {
        item {
            if (state.bannerState.banners.isNotEmpty()) {
                HomeBanner(state = state.bannerState, onEvent = {
                    onEvent(HomeContract.Event.Banner(it))
                })
                SectionSpacer()
            }
        }
        item {
            if (state.popularProducts.isNotEmpty()) {
                HomePopularItemList(
                    state = state,
                    onProductClick = { productId ->
                        onEvent(HomeContract.Event.OnProductClicked(productId = productId))
                    },
                    onLikeClick = { productId ->
                        onEvent(HomeContract.Event.ToggleFavorite(productId = productId))
                    }, onCartClick = { productId ->
                        onEvent(HomeContract.Event.OnAddToCartClick(productId = productId))
                    }
                )
                SectionSpacer()
            }
        }
        item {
            if (state.rankingCategories.isNotEmpty()) {
                HomeCategoryRanking(
                    state = state,
                    onEvent = onEvent,
                    pagerState = homeCategoryRankingPagerState
                )
                SectionSpacer()
            }
        }
        item {
            if (state.saleProductList.isNotEmpty())
                HomeSaleProduct(saleProducts = state.saleProductList)
        }
    }
}

@Composable
private fun SectionSpacer() {
    Spacer(modifier = Modifier.height(Spacing.spacing3))
}