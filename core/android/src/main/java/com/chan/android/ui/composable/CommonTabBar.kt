package com.chan.android.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun CommonTabBar(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    Column(modifier = modifier.background(Color.White)) {
        LazyRow(
            state = lazyListState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            itemsIndexed(items = tabs, key = { index, _ -> index }) { index, title ->
                CustomTab(
                    title = title,
                    isSelected = index == selectedTabIndex,
                    onClick = { onTabClick(index) }
                )
            }
        }
        HorizontalDivider(color = Color(0xFF_D9_DC_E3), thickness = 1.dp)
    }
}

@Composable
fun CustomTab(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var textWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = if (isSelected) Color.Black else Color.Gray,
            onTextLayout = { textLayoutResult ->
                textWidth = with(density) { textLayoutResult.size.width.toDp() }
            },
            modifier = Modifier.padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .width(textWidth)
                .height(2.dp)
                .background(if (isSelected) Color.Black else Color.Transparent)
        )
    }
}