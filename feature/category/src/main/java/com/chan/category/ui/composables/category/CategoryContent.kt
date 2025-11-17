package com.chan.category.ui.composables.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.appTypography
import com.chan.category.ui.model.CategoriesModel

@Composable
fun CategoryContent(
    categories: List<CategoriesModel>,
    state: LazyListState,
    onCategoryClick: (String) -> Unit
) {
    LazyColumn(
        state = state,
        contentPadding = PaddingValues(bottom = 150.dp),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(start = Spacing.spacing5)
    ) {
        categories.forEach { category ->
            item(key = "parent-${category.id}") {
                CategoryHeaderItem(
                    category = category,
                    onClick = { onCategoryClick(category.id) }
                )
            }
            items(category.subCategories, key = { it.id }) { subCategory ->
                SubCategoryItem(
                    subCategory = subCategory,
                    onClick = { onCategoryClick(subCategory.id) }
                )
            }
        }
    }
}

@Composable
fun CategoryHeaderItem(
    category: CategoriesModel,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(top = Spacing.spacing6),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = category.imageUrl,
            contentDescription = "상위 카테고리 이미지",
            modifier = Modifier.size(Spacing.spacing7)
        )
        Spacer(modifier = Modifier.width(Spacing.spacing2))
        Text(
            text = category.name,
            style = MaterialTheme.appTypography.categoryTextStyle,
            modifier = Modifier
                .clickable {
                    onClick()
                }
        )
    }
}

@Composable
fun SubCategoryItem(
    subCategory: CategoriesModel,
    onClick: () -> Unit
) {
    Text(
        text = subCategory.name,
        style = MaterialTheme.appTypography.subCategoryTextStyle,
        modifier = Modifier
            .padding(top = Spacing.spacing6)
            .clickable {
                onClick()
            }
    )
}