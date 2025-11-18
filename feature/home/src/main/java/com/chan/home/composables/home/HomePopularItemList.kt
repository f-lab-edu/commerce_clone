package com.chan.home.composables.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chan.android.model.ProductsModel
import com.chan.android.ui.CommonProductsCard
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.appTypography
import com.chan.android.ui.util.horizontalNestedScrollConnection
import com.chan.home.R
import com.chan.home.home.HomeContract

@Composable
fun HomePopularItemList(
    state: HomeContract.State,
    onProductClick: (productId: String) -> Unit,
    onLikeClick: (productId: String) -> Unit,
    onCartClick: (productId: String) -> Unit
) {
    val nestedScrollConnection = horizontalNestedScrollConnection()
    val productCardSize = 170.dp
    val listState = rememberLazyListState()

    Text(
        text = stringResource(R.string.home_popular_product),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = Spacing.spacing4, top = Spacing.spacing2, bottom = Spacing.spacing1),
        style = MaterialTheme.appTypography.title
    )

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(start = Spacing.spacing2),
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        items(
            items = state.popularProducts,
            key = { it.productId }
        ) { item ->
            CommonProductsCard(
                product = item,
                isLiked = item.productId in state.likedProductIds,
                modifier = Modifier.width(productCardSize),
                onClick = {
                    onProductClick(item.productId)
                },
                onLikeClick = { productId ->
                    onLikeClick(productId)
                },
                onCartClick = { productId ->
                    onCartClick(productId)
                },
            )
        }
    }
}