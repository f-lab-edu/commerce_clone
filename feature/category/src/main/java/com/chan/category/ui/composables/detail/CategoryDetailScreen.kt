package com.chan.category.ui.composables.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chan.android.model.ProductsModel
import com.chan.android.ui.CommonProductsCard
import com.chan.android.ui.composable.CommonTabBar
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.category.ui.CategoryDetailContract
import com.chan.category.ui.model.detail.CategoryDetailTabsModel

@Composable
fun CategoryDetailScreen(
    state: CategoryDetailContract.State,
    onEvent: (CategoryDetailContract.Event) -> Unit
) {
    Scaffold(
        topBar = {
            if (state.categoryNames.isNotEmpty()) {
                CategoryDetailTopBar(
                    tabs = state.categoryNames,
                    selectedIndex = state.selectedCategoryTabIndex,
                    onTabSelected = { categoryId ->
                        onEvent(
                            CategoryDetailContract.Event.CategoryTabSelected(
                                categoryId
                            )
                        )
                    }
                )
            }
        }
    ) { padding ->
        CategoryDetailProductGrid(
            products = state.productListByCategory,
            modifier = Modifier.padding(top = padding.calculateTopPadding()),
            onEvent = onEvent
        )
    }
}

@Composable
fun CategoryDetailProductGrid(
    products: List<ProductsModel>,
    modifier: Modifier = Modifier,
    onEvent: (CategoryDetailContract.Event) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.background(White),
        contentPadding = PaddingValues(Spacing.spacing2)
    ) {
        items(
            items = products,
            key = { it.productId }
        ) { product ->
            CommonProductsCard(
                product = product,
                onClick = { onEvent(CategoryDetailContract.Event.OnProductClick(it)) },
                onLikeClick = {},
                onCartClick = {
                    onEvent(CategoryDetailContract.Event.OnAddToCartClick(it))
                }
            )
        }
    }
}

@Composable
fun CategoryDetailTopBar(
    tabs: List<CategoryDetailTabsModel>,
    selectedIndex: Int,
    onTabSelected: (String) -> Unit
) {

    CommonTabBar(
        tabs = tabs.map { it.categoryName },
        selectedTabIndex = selectedIndex,
        onTabClick = {
            onTabSelected(tabs[it].categoryId)
        }
    )
}
