package com.chan.feature.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chan.feature.ui.category.CategoryScreen
import com.chan.feature.ui.common.BottomNavItem
import com.chan.feature.ui.common.BottomNavigationBar
import com.chan.feature.ui.history.HistoryScreen
import com.chan.feature.ui.home.composables.HomeScreen
import com.chan.feature.ui.mypage.MyPageScreen


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value
    val currentRoute = currentDestination?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        if (route == BottomNavItem.Home.route) popUpTo(0)
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) { HomeScreen() }
            composable(BottomNavItem.Category.route) { CategoryScreen() }
            composable(BottomNavItem.History.route) { HistoryScreen() }
            composable(BottomNavItem.MyPage.route) { MyPageScreen() }
        }
    }
}