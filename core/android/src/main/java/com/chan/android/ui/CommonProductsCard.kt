package com.chan.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.chan.android.model.ProductCardOrientation
import com.chan.android.model.ProductsModel
import com.chan.android.ui.theme.Radius
import com.chan.android.ui.theme.Spacing

@Composable
fun CommonProductsCard(
    product: ProductsModel,
    isLiked: Boolean = false,
    modifier: Modifier = Modifier.width(200.dp),
    orientation: ProductCardOrientation = ProductCardOrientation.VERTICAL,
    showLikeButton: Boolean = true,
    showCartButton: Boolean = true,
    onClick: (productId: String) -> Unit,
    onLikeClick: (productId: String) -> Unit,
    onCartClick: (productId: String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier
            .clickable(
                onClick = { onClick(product.productId) },
                indication = null,
                interactionSource = interactionSource
            ),
        shape = RoundedCornerShape(Radius.radius2),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        if (orientation == ProductCardOrientation.VERTICAL) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.productName,
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Radius.radius2)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(Spacing.spacing3))

                Text(
                    text = product.productName,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(Spacing.spacing3))

                Price(product)
                Spacer(modifier = Modifier.height(Spacing.spacing3))

                if (product.tags.isNotEmpty()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.spacing1)) {
                        product.tags.forEach { tag ->
                            TagChip(tagLabel = tag)
                        }
                    }
                    Spacer(modifier = Modifier.height(Spacing.spacing3))
                }

                Review(product)
                Spacer(modifier = Modifier.height(Spacing.spacing1))

                Row(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                        .offset(x = (-12).dp), // IconButton 기본 내부 padding 상쇄
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showLikeButton) {
                        IconButton(
                            onClick = { onLikeClick(product.productId) },
                        ) {
                            Icon(
                                imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "like",
                                tint = if (isLiked) Color.Red else Color.Gray,
                                modifier = Modifier.size(Spacing.spacing4)
                            )
                        }
                    }
                    if (showCartButton) {
                        IconButton(onClick = { onCartClick(product.productId) }) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "cart",
                                modifier = Modifier.size(Spacing.spacing4)
                            )
                        }
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.productName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    // 상품명
                    Text(
                        text = product.productName,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // 할인 및 가격 정보
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = product.discountPercent,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = product.discountPrice,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = product.originalPrice,
                            style = MaterialTheme.typography.bodySmall,
                            textDecoration = TextDecoration.LineThrough,
                            color = Color.Gray
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (showLikeButton) {
                        IconButton(onClick = { onLikeClick(product.productId) }) {
                            Icon(
                                imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "like",
                                tint = if (isLiked) Color.Red else Color.Gray,
                                modifier = Modifier.size(Spacing.spacing4)
                            )
                        }
                    }
                    if (showCartButton) {
                        IconButton(onClick = { onCartClick(product.productId) }) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "cart",
                                modifier = Modifier.size(Spacing.spacing4)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Price(price: ProductsModel) {
    Text(
        text = price.originalPrice,
        fontSize = 10.sp,
        color = Color.LightGray,
        fontWeight = FontWeight.Medium,
        style = TextStyle(textDecoration = TextDecoration.LineThrough)
    )
    Row {
        Text(
            text = price.discountPercent,
            fontSize = 12.sp,
            color = Color.Red,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = price.discountPrice,
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TagChip(tagLabel: String) {
    val textColor = when (tagLabel) {
        "오늘드림" -> Color(0xFFDD5993)
        else -> Color(0xFF666666)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFFF1F1F1))
            .padding(horizontal = 6.dp)
    ) {
        Text(
            text = tagLabel,
            style = MaterialTheme.typography.labelSmall.copy(
                color = textColor,
                fontSize = 9.sp,
                fontWeight = FontWeight.Normal
            ),
            maxLines = 1

        )
    }
}

@Composable
fun Review(reviews: ProductsModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "star",
            tint = Color.Gray,
            modifier = Modifier.size(10.dp)
        )
        Spacer(modifier = Modifier.width(Spacing.spacing1))
        Text(
            text = reviews.reviewCount,
            fontSize = 10.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Medium
        )
    }
}