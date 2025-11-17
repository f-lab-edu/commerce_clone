package com.chan.category.ui.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chan.android.ui.composable.CommonTopBar
import com.chan.android.ui.composable.MainTopBar
import com.chan.android.ui.theme.Black
import com.chan.android.ui.theme.MainColor
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.category.ui.CategoryContract
import com.chan.category.ui.CategoryViewModel
import com.chan.category.ui.composables.category.CategoryContent
import com.chan.category.ui.composables.category.CategorySidebar
import com.chan.navigation.Routes
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    navController: NavHostController,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {

    val state by categoryViewModel.viewState.collectAsState()
    val contentListState = rememberLazyListState()
    val sidebarListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        categoryViewModel.effect.collectLatest { effect ->
            when (effect) {
                is CategoryContract.Effect.ShowError -> Log.d(
                    "CategoryScreen",
                    " Error : ${effect.errorMessage}"
                )

                is CategoryContract.Effect.Navigation.ToCategoryDetail -> {
                    navController.navigate(
                        Routes.CATEGORY_DETAIL.categoryDetailRoute(effect.categoryId)
                    )
                }

                is CategoryContract.Effect.ScrollToSidebar -> {
                    sidebarListState.scrollToItem(effect.index)
                }

                is CategoryContract.Effect.ScrollToContent -> {
                    contentListState.scrollToItem(
                        effect.index
                    )
                }

                CategoryContract.Effect.Navigation.ToSearchRoute -> {
                    navController.navigate(
                        Routes.SEARCH.route
                    )
                }
            }
        }

    }

    LaunchedEffect(contentListState) {
        snapshotFlow { contentListState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { firstIndex ->
                categoryViewModel.setEvent(CategoryContract.Event.OnContentScroll(firstIndex))
            }
    }

    val topTabs = remember { listOf("항목", "헬스+", "Luxe Edit") }

    val topBarColors = when (state.selectedTabIndex) {
        1 -> TopAppBarDefaults.topAppBarColors(containerColor = MainColor)
        2 -> TopAppBarDefaults.topAppBarColors(containerColor = Black)
        else -> TopAppBarDefaults.topAppBarColors(containerColor = White)
    }
    val tabTextColor = if (state.selectedTabIndex == 0) Black else White
    val tabUnselectedTextColor = if (state.selectedTabIndex == 0) Color.Gray else White.copy(alpha = 0.7f)
    val iconTintColor = if (state.selectedTabIndex == 0) Black else White


    Scaffold(
        topBar = {
            key(state.selectedTabIndex) {
                MainTopBar(
                    navigationIcon = null,
                    titleContent = {
                        CategoryTopTabs(
                            tabs = topTabs,
                            selectedIndex = state.selectedTabIndex,
                            onTabClick = { index ->
                                categoryViewModel.setEvent(CategoryContract.Event.OnTabSelected(index))
                            },
                            selectedTextColor = tabTextColor,
                            unselectedTextColor = tabUnselectedTextColor,
                            indicatorColor = tabTextColor
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            categoryViewModel.setEvent(CategoryContract.Event.OnSearchClick)
                        }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "검색",
                                tint = iconTintColor
                            )
                        }
                    },
                    colors = topBarColors
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            when (state.selectedTabIndex) {
                1 -> HealthPlusBanner()
                2 -> LuxeEditBanner()
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CategorySidebar(
                    categories = state.categories,
                    selectedCategoryId = state.selectedCategoryId,
                    listState = sidebarListState,
                    onCategoryClick = { categoryId ->
                        categoryViewModel.setEvent(
                            CategoryContract.Event.OnCategorySidebarClick(
                                categoryId
                            )
                        )
                    }
                )
                when (state.selectedTabIndex) {
                    0 -> CategoryContent(
                        categories = state.categories,
                        state = contentListState,
                        onCategoryClick = { categoryId ->
                            categoryViewModel.setEvent(
                                CategoryContract.Event.OnCategoryClick(categoryId)
                            )
                        }
                    )

                    1 -> HealthPlusContent()
                    2 -> LuxeEditContent()
                }
            }
        }
    }
}

@Composable
fun HealthPlusBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MainColor)
    ) {
        Text(
            text = "Health+ 테마관 바로 가기 >",
            modifier = Modifier.padding(Spacing.spacing4),
            color = White
        )
    }
}

@Composable
fun LuxeEditBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Black)
    ) {
        Text(
            text = "Luxe Edit 테마관 바로 가기 >",
            modifier = Modifier.padding(Spacing.spacing4),
            color = White
        )
    }
}

@Composable
fun HealthPlusContent() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(White), contentAlignment = Alignment.Center) {
        Text("Health+ Content")
    }
}

@Composable
fun LuxeEditContent() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Luxe Edit Content")
    }
}

@Composable
fun CategoryTopTabs(
    tabs: List<String>,
    selectedIndex: Int,
    onTabClick: (Int) -> Unit,
    selectedTextColor: Color,
    unselectedTextColor: Color,
    indicatorColor: Color
) {
    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.spacing4)) {
        tabs.forEachIndexed { index, title ->
            CommonTopBar(
                title = title,
                isSelected = index == selectedIndex,
                onClick = { onTabClick(index) },
                selectedTextColor = selectedTextColor,
                unselectedTextColor = unselectedTextColor,
                indicatorColor = indicatorColor
            )
        }
    }
}