package com.chan.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

interface Navigator {
    suspend fun navigate(
        navController: NavHostController,
        route: String,
        builder: NavOptionsBuilder.() -> Unit = {}
    )

    fun navigateUp(navController: NavHostController)
}