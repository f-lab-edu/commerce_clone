package com.chan.cart.naivgation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.chan.cart.CartContract
import com.chan.cart.CartViewModel
import com.chan.cart.ui.CartScreen
import com.chan.cart.ui.popup.CartPopupScreen
import com.chan.navigation.NavGraphProvider
import javax.inject.Inject

class CartNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    ) {
        navGraphBuilder.composable(CartDestination.route) { backStackEntry ->
            CartRoute(navController)
        }

        navGraphBuilder.dialog(CartPopupDestination.route) { backStackEntry ->
            val productId = requireNotNull(backStackEntry.arguments?.getString("productId")) {
                "product is required"
            }

            CartPopupRoute(navController, productId)
        }
    }
}

@Composable
fun CartRoute(navController: NavHostController) {
    val viewModel: CartViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsState()

    CartScreen(
        state = state,
        onEvent = viewModel::setEvent
    )
}


@Composable
fun CartPopupRoute(navController: NavHostController, productId: String) {
    val viewModel: CartViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect
            .collect { effect ->
                when (effect) {
                    is CartContract.Effect.ShowToast -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }

                    is CartContract.Effect.ShowError -> {
                        Toast.makeText(context, effect.errorMsg, Toast.LENGTH_SHORT).show()
                    }

                    CartContract.Effect.DismissCartPopup -> {
                        navController.popBackStack()
                    }
                }
            }
    }

    LaunchedEffect(key1 = productId) {
        viewModel.handleEvent(CartContract.Event.LoadPopupProductInfo(productId))
    }

    CartPopupScreen(
        state = state,
        onEvent = viewModel::setEvent,
        onDismiss = {
            navController.popBackStack()
        }
    )
}