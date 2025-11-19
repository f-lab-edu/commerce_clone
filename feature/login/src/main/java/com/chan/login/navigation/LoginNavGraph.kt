package com.chan.login.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.login.ui.LoginContract
import com.chan.login.ui.LoginViewModel
import com.chan.login.ui.composables.LoginScreen
import com.chan.navigation.NavGraphProvider
import com.chan.navigation.Navigator
import com.chan.navigation.Routes
import javax.inject.Inject

class LoginNavGraph @Inject constructor(
    private val navigator: Navigator,
) : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    ) {
        navGraphBuilder.composable(
            route = LoginDestination.route,
            arguments = LoginDestination.arguments
        ) { backStackEntry ->
            val redirectRoute = backStackEntry.arguments?.getString("redirect") ?: ""

            LoginRoute(navController, redirectRoute, navigator)
        }
    }
}


@Composable
fun LoginRoute(navController: NavHostController, redirectRoute: String, navigator: Navigator) {
    val viewModel: LoginViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginContract.Effect.NavigateToHome -> {
                    val target =
                        if (redirectRoute.isNotEmpty()) redirectRoute else Routes.MYPAGE.route
                    navigator.navigate(
                        navController = navController,
                        route = target,
                        builder = {
                            if (target == Routes.HOME.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                            } else {
                                popUpTo(Routes.LOGIN.route) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }

                is LoginContract.Effect.ShowError -> {
                    val errorMsg = context.getString(effect.errorMsg)
                    Log.e("LoginErrorInfo", errorMsg)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(LoginContract.Event.CheckUserSession)
    }


    if (state.isSessionCheckCompleted) {
        LoginScreen(
            state = state,
            onEvent = viewModel::setEvent
        )
    }
}