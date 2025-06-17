package com.chan.feature.ui.home.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chan.feature.ui.home.HomeContract
import com.chan.feature.ui.home.HomeViewModel
import com.chan.feature.ui.home.model.HomeTabItem

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val state = homeViewModel.viewState.value
    val tabList = HomeTabItem.tabList()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabList.size }
    )

    LaunchedEffect(Unit) {
        homeViewModel.setEvent(HomeContract.Event.BannerLoad)
        homeViewModel.setEvent(HomeContract.Event.PopularItemLoad)
    }

    Scaffold(
        topBar = { HomeTopTab(tabList = tabList, pagerState = pagerState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (tabList[pagerState.currentPage] == HomeTabItem.Home) {
                HomeBanner(bannerList = state.bannerList)
                Spacer(modifier = Modifier.height(12.dp))
                HomePopularItemList(popularItem = state.popularItemList)
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (tabList[page]) {
                    HomeTabItem.RecommendToday -> RecommendScreen()
                    // 나머지 탭 추후 구현
                    else -> {}
                }
            }
        }
    }
}