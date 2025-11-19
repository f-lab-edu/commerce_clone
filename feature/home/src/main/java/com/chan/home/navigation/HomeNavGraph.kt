package com.chan.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chan.android.ToastUtil
import com.chan.home.composables.HomeScreen
import com.chan.home.composables.home.banner.HomeBannerWebViewScreen
import com.chan.home.home.HomeContract
import com.chan.home.home.HomeViewModel
import com.chan.navigation.NavGraphProvider
import com.chan.navigation.Navigator
import com.chan.navigation.Routes
import kotlinx.coroutines.flow.filterIsInstance
import javax.inject.Inject

class HomeNavGraph @Inject constructor(
    private val navigator: Navigator,
) : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    ) {
        navGraphBuilder.composable(HomeDestination.route) {
            HomeRoute(navigator, navController)
        }

        navGraphBuilder.composable(
            route = Routes.HOME_BANNER_WEB_VIEW.route,
            arguments = listOf(
                navArgument("url") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""

            val viewModel: HomeViewModel = hiltViewModel()

            HomeBannerWebViewScreen(
                url = url,
                onEvent = viewModel::setEvent,
                effect = viewModel.effect
            )
        }
    }
}

@Composable
fun HomeRoute(navigator: Navigator, navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.setEvent(HomeContract.Event.Banner(HomeContract.BannerEvent.LoadBanners))
        viewModel.setEvent(HomeContract.Event.PopularItemLoad)
        viewModel.setEvent(HomeContract.Event.RankingCategoryTabsLoad)
        viewModel.setEvent(HomeContract.Event.SaleProducts)
    }

    LaunchedEffect(Unit) {
        viewModel.effect
            .filterIsInstance<HomeContract.Effect.Navigation>()
            .collect { effect ->
                ToastUtil.cancel()
                when (effect) {
                    is HomeContract.Effect.Navigation.ToProductDetailRoute ->
                        navigator.navigate(
                            navController = navController,
                            route = Routes.PRODUCT_DETAIL.productDetailRoute(effect.productId)
                        )

                    is HomeContract.Effect.Navigation.ToCartPopupRoute ->
                        navigator.navigate(
                            navController = navController,
                            route = Routes.CART_POPUP.cartPopUpRoute(effect.productId)
                        )

                    is HomeContract.Effect.Navigation.ToCartRoute ->
                        navigator.navigate(
                            navController = navController,
                            route = Routes.CART.route
                        )

                    HomeContract.Effect.Navigation.ToSearchRoute ->
                        navigator.navigate(
                            navController = navController,
                            route = Routes.SEARCH.route
                        )

                    is HomeContract.Effect.Navigation.ToWebView ->
                        navigator.navigate(
                            navController = navController,
                            route = Routes.HOME_BANNER_WEB_VIEW.homeBannerWebViewRoute(effect.url)
                        )

                    HomeContract.Effect.Navigation.ToNotification ->
                        ToastUtil.show(context, "준비 중입니다.")
                }
            }
    }


    LaunchedEffect(Unit) {
        viewModel.effect
            .filterIsInstance<HomeContract.Effect.ShowError>()
            .collect { error ->
                ToastUtil.show(context, error.message)
            }
    }

    HomeScreen(
        state = state,
        onEvent = viewModel::setEvent
    )
}