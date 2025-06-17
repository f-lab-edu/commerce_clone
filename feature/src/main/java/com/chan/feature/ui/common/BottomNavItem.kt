package com.chan.feature.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Category : BottomNavItem("category", "카테고리", Icons.Default.Menu)
    object Home : BottomNavItem("home", "홈", Icons.Default.Home)
    object History : BottomNavItem("hisoty", "히스토리", Icons.Default.Refresh)
    object MyPage : BottomNavItem("mypage", "마이", Icons.Default.Person)

    companion object {
        val bottomNavItems = listOf(Category, Home, History, MyPage)
    }
}
