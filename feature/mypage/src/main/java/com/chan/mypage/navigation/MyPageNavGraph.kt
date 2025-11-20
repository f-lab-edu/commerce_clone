package com.chan.mypage.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.chan.mypage.MyPageContract
import com.chan.mypage.MyPageScreen
import com.chan.mypage.MyPageViewModel
import com.chan.navigation.NavGraphProvider
import com.chan.navigation.Routes
import javax.inject.Inject

class MyPageNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(MyPageDestination.route) {
            MyPageRoute(navController)
        }
    }
}


@Composable
fun MyPageRoute(navController: NavHostController) {
    val viewModel: MyPageViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect) {
                MyPageContract.Effect.Navigation.ToHome -> {
                    navController.navigate(Routes.HOME.route) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
                is MyPageContract.Effect.ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    val orderItems = viewModel.orderPagingFlow.collectAsLazyPagingItems()

    MyPageScreen(
        state = state,
        orderItems = orderItems,
        onEvent = viewModel::setEvent
    )
}