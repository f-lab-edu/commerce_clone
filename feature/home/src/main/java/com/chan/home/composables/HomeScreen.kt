package com.chan.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.chan.android.model.MainTopBarAction
import com.chan.android.ui.composable.MainTopBar
import com.chan.android.ui.theme.White
import com.chan.home.R
import com.chan.home.composables.home.HomePage
import com.chan.home.composables.home.HomeTopTab
import com.chan.home.composables.ranking.HomeRankingScreen
import com.chan.home.home.HomeContract
import com.chan.home.model.HomeTabItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit,
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.topBars.size }
    )
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            Column {
                MainTopBar(
                    navigationIcon = null,
                    titleContent = {
                        Text(
                            text = stringResource(R.string.main_title)
                        )
                    },
                    actions = {
                        IconButton(onClick = { onEvent(HomeContract.Event.OnSearchClick) }) {
                            Icon(Icons.Default.Search, contentDescription = "검색")
                        }
                        IconButton(onClick = { onEvent(HomeContract.Event.NavigateToNotification) }) {
                            Icon(Icons.Default.Notifications, contentDescription = "알림")
                        }
                        IconButton(onClick = { onEvent(HomeContract.Event.OnCartClick) }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "장바구니")
                        }
                    },
                    scrollBehavior = scrollBehavior
                )

                HomeTopTab(
                    tabs = state.topBars.map { stringResource(id = it.titleResId) },
                    selectedTabIndex = pagerState.currentPage,
                    onTabClick = { index ->
                        scope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
                .padding(top = innerPadding.calculateTopPadding())
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { page ->
            when (state.topBars[page]) {
                HomeTabItem.Home -> {
                    HomePage(
                        state = state,
                        onEvent = onEvent
                    )
                }

                HomeTabItem.RecommendToday -> RecommendScreen()
                HomeTabItem.Ranking -> HomeRankingScreen(
                    state = state,
                    onEvent = onEvent
                )

                else -> {}
            }
        }
    }
}