package com.chan.home.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.chan.android.BaseViewModel
import com.chan.android.model.ProductsModel
import com.chan.home.domain.HomeUseCases
import com.chan.home.domain.usecase.RankingUseCase
import com.chan.home.home.HomeContract.BannerEvent
import com.chan.home.home.HomeContract.BannerEvent.IssuedCoupon
import com.chan.home.home.HomeContract.BannerEvent.LoadBanners
import com.chan.home.home.HomeContract.BannerEvent.SelectBanner
import com.chan.home.home.HomeContract.Effect.Banner.ShowCouponDownloaded
import com.chan.home.home.HomeContract.Effect.Navigation.*
import com.chan.home.home.HomeContract.Effect.ShowError
import com.chan.home.mapper.toPresentation
import com.chan.home.mapper.toProductsModel
import com.chan.home.mapper.toRankingCategoryTabsModel
import com.chan.home.mapper.toSaleProductModel
import com.chan.home.model.HomeBannerModel
import com.chan.home.model.HomeTabItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val rankingUseCases: RankingUseCase
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    private val _rankingProducts = MutableStateFlow<Flow<PagingData<ProductsModel>>?>(null)
    val rankingProducts: StateFlow<Flow<PagingData<ProductsModel>>?> =
        _rankingProducts.asStateFlow()

    init {
        loadHomeTopBar()
    }

    override fun setInitialState() = HomeContract.State()

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.PopularItemLoad -> loadPopularProducts()
            HomeContract.Event.RankingCategoryTabsLoad -> loadRankingCategoryTabs()
            is HomeContract.Event.RankingTabClicked -> {
                setState { copy(selectedRankingTabIndex = event.index) }
            }

            is HomeContract.Event.RankingTabSelected -> getRankingCategories(event.categoryId)
            HomeContract.Event.SaleProducts -> getSaleProducts()
            is HomeContract.Event.OnProductClicked -> productClicked(event.productId)
            is HomeContract.Event.OnLikedClick -> {}
            HomeContract.Event.OnCartClick -> setEffect { ToCartRoute }
            is HomeContract.Event.OnAddToCartClick -> setEffect { ToCartPopupRoute(event.productId) }
            HomeContract.Event.HomeRankingEvent.RankingProductsLoad -> loadRankingProducts()
            HomeContract.Event.OnSearchClick -> setEffect { ToSearchRoute }
            is HomeContract.Event.Banner -> handleBannerEvent(event.event)
            HomeContract.Event.NavigateToNotification -> setEffect { ToNotification }
        }
    }

    private fun handleBannerEvent(event: BannerEvent) {
        when (event) {
            is LoadBanners -> loadBanners()
            is SelectBanner -> handleBannerClick(event.bannerModel)
            is IssuedCoupon -> setEffect { ShowCouponDownloaded }
        }
    }

    private fun handleBannerClick(bannerModel: HomeBannerModel) {
        when (bannerModel.linkType) {
            "WEB" -> setEffect { ToWebView(bannerModel.linkUrl) }
            else -> setEffect { ShowError("잘못된 링크입니다.") }
        }
    }

    private fun loadRankingProducts() {
        if (_rankingProducts.value != null) return

        _rankingProducts.value = rankingUseCases.rankingProductsUseCase.invoke()
            .map { it.map { it.toProductsModel() } }
            .cachedIn(viewModelScope)
    }

    private fun loadHomeTopBar() {
        setState { copy(topBars = HomeTabItem.tabList()) }
    }

    private fun productClicked(productId: String) {
        setEffect { ToProductDetailRoute(productId) }
    }

    private fun loadBanners() {
        handleRepositoryCall(
            call = {
                homeUseCases.homeBanner.invoke().map { it.toPresentation() }
            },
            onSuccess = { bannerList -> copy(bannerState = bannerState.copy(banners = bannerList)) }
        )
    }

    private fun loadPopularProducts() {
        viewModelScope.launch {
            homeUseCases.popularProducts.invoke(20)
                .map { list -> list.map { it.toProductsModel() } }
                .collect { products ->
                    setState { copy(popularProducts = products) }
                }
        }
    }

    private fun loadRankingCategoryTabs() {
        handleRepositoryCall(
            call = {
                homeUseCases.categoryTabs.invoke().map { it.toRankingCategoryTabsModel() }
            },
            onSuccess = { rankingCategoryTabs -> copy(rankingCategoryTabs = rankingCategoryTabs) },
            onFinally = { tabs ->
                if (tabs.isNotEmpty())
                    getRankingCategories(tabs.first().id)
            }
        )
    }

    private fun getRankingCategories(categoryId: String) {
        viewModelScope.launch {
            homeUseCases.categoryRankingProducts.invoke((categoryId))
                .map { list -> list.map { it.toProductsModel() } }
                .collect { rankingCategoryProducts ->
                    setState { copy(rankingCategories = rankingCategoryProducts) }
                }
        }
    }

    private fun getSaleProducts() {
        handleRepositoryCall(
            call = { homeUseCases.saleProducts.invoke(20).map { it.toSaleProductModel() } },
            onSuccess = { saleProducts -> copy(saleProductList = saleProducts) }
        )
    }

    private fun <T> handleRepositoryCall(
        call: suspend () -> T,
        onSuccess: HomeContract.State.(T) -> HomeContract.State,
        onFinally: suspend (T) -> Unit = {}
    ) {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            try {
                val result = call()
                setState { onSuccess(result) }
                onFinally(result)

            } catch (e: Exception) {
                setState { copy(isLoading = false, isError = true) }
                setEffect { HomeContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }
}