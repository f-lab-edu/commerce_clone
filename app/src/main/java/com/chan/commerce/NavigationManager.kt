package com.chan.commerce

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.chan.auth.domain.usecase.CheckSessionUseCase
import com.chan.navigation.Navigator
import com.chan.navigation.createLoginRoute
import com.chan.navigation.routesRequiringAuth
import javax.inject.Inject

class NavigationManager @Inject constructor(
    private val checkSessionUseCase: CheckSessionUseCase,
) : Navigator {

    override suspend fun navigate(
        navController: NavHostController,
        route: String,
        builder: NavOptionsBuilder.() -> Unit,
    ) {
        val requiresLogin = routesRequiringAuth.any { route.startsWith(it) }
        val loggedIn = checkSessionUseCase()

        if (requiresLogin && !loggedIn) {
            navController.navigate(createLoginRoute(route))
            builder
            return
        }
        navController.navigate(route, builder)
    }

    override fun navigateUp(navController: NavHostController) {
        navController.navigateUp()
    }
}
