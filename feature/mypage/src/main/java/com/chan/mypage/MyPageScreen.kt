package com.chan.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.chan.android.model.ProductCardOrientation
import com.chan.android.ui.CommonProductsCard
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.mypage.ui.OrdersModel

@Composable
fun MyPageScreen(
    state: MyPageContract.State,
    orderItems: LazyPagingItems<OrdersModel>,
    onEvent: (MyPageContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(Spacing.spacing4),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "마이 페이지 입니다."
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing4),
            onClick = { onEvent(MyPageContract.Event.OnLogoutClicked) }) {
            Text("로그아웃")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(
                count = orderItems.itemCount,
                key = { orderItems[it]?.orderId ?: "order-placeholder-$it" }
            ) { index ->
                val item = orderItems[index]
                item?.let {
                    Text(
                        text = it.orderData,
                        modifier = Modifier.padding(Spacing.spacing2)
                    )

                    it.items.forEach {
                        CommonProductsCard(
                            product = it,
                            orientation = ProductCardOrientation.HORIZONTAL,
                            modifier = Modifier.fillMaxSize(),
                            showCartButton = false,
                            showLikeButton = false
                        )

                        Box(
                            modifier = Modifier
                                .padding(horizontal = Spacing.spacing2)
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFE0E0E0),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .background(White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "배송조회",
                                color = Color.Black,
                                modifier = Modifier.padding(vertical = Spacing.spacing2)
                            )
                        }
                    }
                }
            }
        }
    }
}