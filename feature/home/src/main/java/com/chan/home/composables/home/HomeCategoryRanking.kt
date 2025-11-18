package com.chan.home.composables.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.chan.android.model.ProductCardOrientation
import com.chan.android.ui.CommonProductsCard
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.util.horizontalNestedScrollConnection
import com.chan.home.R
import com.chan.home.home.HomeContract
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeCategoryRanking(
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit,
    pagerState: PagerState,
) {

    val scope = rememberCoroutineScope()
    val nestedScrollConnection = horizontalNestedScrollConnection()

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress && state.rankingCategoryTabs.isNotEmpty()) {
            val categoryId = state.rankingCategoryTabs[pagerState.currentPage].id
            onEvent(HomeContract.Event.RankingTabSelected(categoryId))
        }
    }

    LaunchedEffect(state.selectedRankingTabIndex) {
        scope.launch {
            pagerState.scrollToPage(state.selectedRankingTabIndex)
        }
    }

    Text(
        text = stringResource(R.string.home_category_ranking),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = Spacing.spacing4, top = Spacing.spacing2, bottom = Spacing.spacing1),
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
    )

    Column {
        CategoryTab(
            categoryTabs = state.rankingCategoryTabs,
            selectedCategoryTabIndex = pagerState.currentPage,
            onSelectedChanged = { idx ->
                onEvent(HomeContract.Event.RankingTabClicked(idx))
            }
        )
        Spacer(Modifier.height(Spacing.spacing1))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .nestedScroll(nestedScrollConnection)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                state.rankingCategories.forEach { product ->
                    CommonProductsCard(
                        product = product,
                        isLiked = product.productId in state.likedProductIds,
                        modifier = Modifier.fillMaxWidth(),
                        orientation = ProductCardOrientation.HORIZONTAL,
                        showLikeButton = true,
                        showCartButton = true,
                        onClick = { productId ->
                            onEvent(HomeContract.Event.OnProductClicked(productId = productId))
                        },
                        onLikeClick = { productId ->
                            onEvent(HomeContract.Event.ToggleFavorite(productId = productId))
                        },
                        onCartClick = { productId ->
                            onEvent(HomeContract.Event.OnAddToCartClick(productId = productId))
                        }
                    )
                }
            }
        }
    }
}