package com.chan.cart

import androidx.annotation.StringRes
import com.chan.android.LoadingState
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.android.state.SessionState
import com.chan.cart.model.CartInProductsModel
import com.chan.cart.model.CartInTobBarModel
import com.chan.cart.model.PopupProductInfoModel

class CartContract {
    sealed class Event : ViewEvent {
        data class SelectedTab(val index: Int) : Event()
        data class LoadPopupProductInfo(val productId: String) : Event()
        object LoadCartProducts : Event()
        object CheckUserSession : Event()
        data class AddToProduct(val productId: String) : Event()
        data class UpdateProductSelected(val productId: String, val isSelected: Boolean) : Event()
        data class UpdateProductQuantity(val productId: String, val isAdd: Boolean) : Event()
        object OnAllSelected : Event()
        data class DeleteProduct(val productId: String) : Event()

        object ConfirmPurchase : Event()
        object ConfirmGift : Event()
    }

    data class State(
        val selectedTabIndex: Int = 0,
        val tobBars: List<CartInTobBarModel> = emptyList(),
        val popupProductInfo: PopupProductInfoModel = PopupProductInfoModel.EMPTY,
        val cartInProducts: List<CartInProductsModel> = emptyList(),
        val totalProductsCount: Int = 0,
        val totalPrice: Int = 0,
        val allSelected: Boolean = false,
        val loadingState: LoadingState = LoadingState.Idle,
        override val isSessionCheckCompleted: Boolean = false,
    ) : ViewState, SessionState

    sealed class Effect : ViewEffect {
        data class ShowError(val errorMsg: String) : Effect()
        data class ShowToast(@StringRes val message: Int) : Effect()
        object DismissCartPopup : Effect()

        sealed class Navigation : Effect() {
            object ToLogin : Navigation()
        }
    }
}