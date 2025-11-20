package com.chan.cart.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chan.android.ui.composable.CommonTopBar
import com.chan.android.ui.composable.MainTopBar
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.dividerColor
import com.chan.cart.CartContract
import com.chan.cart.R
import com.chan.cart.ui.composable.CartBottomBar
import com.chan.cart.ui.composable.PickUpScreen
import com.chan.cart.ui.composable.TodayDeliveryScreen
import com.chan.cart.ui.composable.commonCartScreen

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    state: CartContract.State,
    onEvent: (CartContract.Event) -> Unit,
) {
    Scaffold(
        bottomBar = {
            if (state.cartInProducts.isNotEmpty()) {
                CartBottomBar(
                    totalItemCount = state.totalProductsCount,
                    totalPrice = state.totalPrice,
                    onPurchaseClick = { onEvent(CartContract.Event.ConfirmPurchase) }
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {
            MainTopBar(
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "뒤로가기"
                        )
                    }
                },
                titleContent = {
                    Text(text = "장바구니")
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "검색"
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.Home, contentDescription = "홈")
                    }
                },
                centerAligned = true
            )
            Column(modifier = Modifier.background(Color.White)) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    state.tobBars.forEachIndexed { index, bar ->
                        CommonTopBar(
                            title = bar.title,
                            isSelected = index == state.selectedTabIndex,
                            onClick = { onEvent(CartContract.Event.SelectedTab(index)) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                HorizontalDivider(color = dividerColor, thickness = 1.dp)
            }

            if (state.cartInProducts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.empty_cart_in))
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    when (state.selectedTabIndex) {
                        0 -> commonCartScreen(state = state, onEvent = onEvent)
                        1 -> item { TodayDeliveryScreen(modifier = Modifier.fillParentMaxSize()) }
                        2 -> item { PickUpScreen(modifier = Modifier.fillParentMaxSize()) }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    val dummyState = CartContract.State()
    CartScreen(state = dummyState, onEvent = {})
}