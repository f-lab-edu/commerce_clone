package com.chan.home.home

import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.android.model.ProductsModel
import com.chan.home.model.HomeBannerModel
import com.chan.home.model.HomeRankingCategoryTabModel
import com.chan.home.model.HomeSaleProductModel
import com.chan.home.model.HomeTabItem

class HomeContract {
    sealed class Event : ViewEvent {
        data class Banner(val event: BannerEvent) : Event()

        object PopularItemLoad : Event()
        object RankingCategoryTabsLoad : Event()
        object SaleProducts : Event()
        object NavigateToNotification: Event()

        data class RankingTabClicked(val index: Int) : Event()
        data class RankingTabSelected(val categoryId: String) : Event()
        data class OnProductClicked(val productId: String) : Event()
        data class OnLikedClick(val productId: String) : Event()
        object OnCartClick : Event()
        data class OnAddToCartClick(val productId: String) : Event()
        object OnSearchClick : Event()
        data class ToggleFavorite(val productId: String) : Event()

        sealed class HomeRankingEvent : Event() {
            object RankingProductsLoad : HomeRankingEvent()
        }
    }

    sealed class BannerEvent {
        object LoadBanners : BannerEvent()
        data class SelectBanner(val bannerModel: HomeBannerModel) : BannerEvent()
        data class IssuedCoupon(val couponId: String) : BannerEvent()
    }

    data class State(
        val bannerState: BannerState = BannerState(),

        val topBars: List<HomeTabItem> = emptyList(),
        val popularProducts: List<ProductsModel> = emptyList(),
        val rankingCategoryTabs: List<HomeRankingCategoryTabModel> = emptyList(),
        val rankingCategories: List<ProductsModel> = emptyList(),
        val saleProductList: List<HomeSaleProductModel> = emptyList(),
        val selectedRankingTabIndex: Int = 0,
        val likedProductIds: Set<String> = emptySet(),
        val likedProducts: List<ProductsModel> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false,
    ) : ViewState

    data class BannerState(
        val banners: List<HomeBannerModel> = emptyList()
    )

    sealed class Effect : ViewEffect {
        data class ShowError(val message: String) : Effect()

        sealed class Banner : Effect() {
            object ShowCouponDownloaded: Banner()
        }

        sealed class Navigation : Effect() {
            data class ToProductDetailRoute(val productId: String) : Navigation()
            data class ToCartPopupRoute(val productId: String) : Navigation()
            object ToCartRoute : Navigation()
            object ToSearchRoute : Navigation()
            object ToNotification : Navigation()
            data class ToWebView(val url: String) : Navigation()
        }
    }
}