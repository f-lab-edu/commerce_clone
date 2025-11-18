package com.chan.like

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.chan.android.model.ProductCardOrientation
import com.chan.android.ui.CommonProductsCard
import com.chan.android.ui.composable.MainTopBar
import com.chan.android.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikeScreen(
    state: LikeContract.State,
    onEvent: (LikeContract.Event) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            Column {
                MainTopBar(
                    navigationIcon = null,
                    titleContent = {
                        Text(text = stringResource(R.string.history))
                    },
                    actions = {
                        IconButton(onClick = { onEvent(LikeContract.Event.TopBar.NavigateToSearch) }) {
                            Icon(Icons.Default.Search, contentDescription = "검색")
                        }
                        IconButton(onClick = { onEvent(LikeContract.Event.TopBar.NavigateToCart) }) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "장바구니")
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(White)) {
            state.likeProducts.forEach { product ->
                CommonProductsCard(
                    product = product,
                    isLiked = product.productId in state.likedProductIds,
                    modifier = Modifier.fillMaxWidth(),
                    orientation = ProductCardOrientation.HORIZONTAL,
                    onClick = { onEvent(LikeContract.Event.LikeAction.NavigateToProductDetail(it)) },
                    onLikeClick = { onEvent(LikeContract.Event.LikeAction.ToggleFavorite(it)) },
                    onCartClick = { onEvent(LikeContract.Event.LikeAction.ShowCartPopup(it)) }
                )
            }

        }
    }
}