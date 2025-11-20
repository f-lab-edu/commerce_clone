package com.chan.mypage

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.chan.android.BaseViewModel
import com.chan.mypage.MyPageContract.Effect.Navigation.ToHome
import com.chan.mypage.domain.usecase.LogoutUseCase
import com.chan.mypage.domain.usecase.order.GetOrdersPagingUseCase
import com.chan.mypage.ui.OrdersModel
import com.chan.mypage.ui.mappers.toOrdersModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getOrdersPagingUseCase: GetOrdersPagingUseCase,
) : BaseViewModel<MyPageContract.Event, MyPageContract.State, MyPageContract.Effect>() {

    val orderPagingFlow: Flow<PagingData<OrdersModel>> =
        getOrdersPagingUseCase()
            .map { pagingData -> pagingData.map { it.toOrdersModel() } }
            .cachedIn(viewModelScope)


    override fun setInitialState() = MyPageContract.State()

    override fun handleEvent(event: MyPageContract.Event) {
        when (event) {
            MyPageContract.Event.OnLogoutClicked -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            setEffect { ToHome }
        }
    }
}