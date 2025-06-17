package com.chan.feature.ui.home.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RecommendScreen() {
    Text(
        text = "추천",
        modifier = Modifier.fillMaxSize(),
        style = MaterialTheme.typography.headlineMedium
    )
}