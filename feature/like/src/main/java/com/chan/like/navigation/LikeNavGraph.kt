package com.chan.like.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.like.LikeContract
import com.chan.like.LikeScreen
import com.chan.like.LikeViewModel
import com.chan.navigation.NavGraphProvider
import com.chan.navigation.Routes
import kotlinx.coroutines.flow.filterIsInstance
import javax.inject.Inject

class LikeNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    ) {
        navGraphBuilder.composable(
            route = LikeDestination.route,
            arguments = LikeDestination.arguments
        ) { backStackEntry ->

            LikeRoute(navController)
        }
    }
}

@Composable
fun LikeRoute(navController: NavHostController) {
    val viewModel: LikeViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setEvent(LikeContract.Event.LoadFavorites)
    }

    LaunchedEffect(Unit) {
        viewModel.effect
            .filterIsInstance<LikeContract.Effect.Navigation>()
            .collect { effect ->
                when (effect) {
                    LikeContract.Effect.Navigation.ToCartRoute -> navController.navigate(Routes.CART.route)
                    LikeContract.Effect.Navigation.ToSearchRoute -> navController.navigate(Routes.SEARCH.route)
                    is LikeContract.Effect.Navigation.ToCartPopupRoute -> navController.navigate(
                        Routes.CART_POPUP.cartPopUpRoute(effect.productId)
                    )

                    is LikeContract.Effect.Navigation.ToProductDetail -> navController.navigate(
                        Routes.PRODUCT_DETAIL.productDetailRoute(effect.productId)
                    )
                }
            }
    }

    LikeScreen(
        state = state,
        onEvent = viewModel::setEvent
    )
}