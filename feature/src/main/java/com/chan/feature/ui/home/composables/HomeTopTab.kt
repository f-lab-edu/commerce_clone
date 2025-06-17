package com.chan.feature.ui.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chan.feature.ui.home.model.HomeTabItem
import kotlinx.coroutines.launch

@Composable
fun HomeTopTab(tabList: List<HomeTabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    val noRipple = remember { MutableInteractionSource() }

    Column {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 16.dp,
            containerColor = Color.White,
            indicator = { tabPositions ->
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .height(2.dp)
                        .background(Color.Black)
                )
            },
        ) {
            tabList.forEachIndexed { index, tabItem ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = tabItem.title,
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { noRipple },
                                    indication = null
                                ) {
                                    scope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                            color = if (pagerState.currentPage == index) Color.Black else Color.Gray
                        )
                    }
                )
            }
        }
    }
}