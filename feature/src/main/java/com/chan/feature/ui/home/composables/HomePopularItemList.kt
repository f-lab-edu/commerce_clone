package com.chan.feature.ui.home.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chan.feature.ui.home.model.HomePopularItemModel

@Composable
fun HomePopularItemList(
    popularItem: List<HomePopularItemModel>
) {

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(popularItem) { item ->
            HomePopularItem(popularItem = item)
        }
    }
}

@Composable
fun HomePopularItem(popularItem: HomePopularItemModel) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .padding(end = 12.dp)
    ) {
        AsyncImage(
            model = popularItem.imageUrl,
            contentDescription = popularItem.name,
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = popularItem.name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = popularItem.discountPercent,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = popularItem.originPrice,
                style = MaterialTheme.typography.labelSmall.copy(
                    textDecoration = TextDecoration.LineThrough,
                    color = Color.Gray
                )
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = popularItem.discountedPrice,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "평점",
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = popularItem.rating,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }
    }
}