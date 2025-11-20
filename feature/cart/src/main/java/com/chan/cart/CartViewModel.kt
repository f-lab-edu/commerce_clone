package com.chan.cart

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.android.LoadingState
import com.chan.auth.domain.usecase.CheckSessionUseCase
import com.chan.cart.CartContract.Effect.Navigation.ToLogin
import com.chan.cart.domain.usecase.CartUseCases
import com.chan.cart.model.CartInTobBarModel
import com.chan.cart.model.DeliveryType
import com.chan.cart.ui.mapper.toDataStoreCartInProductsModel
import com.chan.cart.ui.mapper.toPopupProductInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val checkSessionUseCase: CheckSessionUseCase,
    private val cartUseCases: CartUseCases
) : BaseViewModel<CartContract.Event, CartContract.State, CartContract.Effect>() {

    init {
        loadCartInTobBar()
        loadCartInProducts()
    }

    override fun setInitialState() = CartContract.State()

    override fun handleEvent(event: CartContract.Event) {
        when (event) {
            is CartContract.Event.SelectedTab -> selectedTab(event.index)
            is CartContract.Event.LoadPopupProductInfo -> loadPopupProductInfo(event.productId)
            is CartContract.Event.LoadCartProducts -> loadCartInProducts()
            is CartContract.Event.AddToProduct -> addToCart(event.productId)
            is CartContract.Event.UpdateProductSelected -> updateProductSelected(
                event.productId,
                event.isSelected
            )

            is CartContract.Event.UpdateProductQuantity -> updateProductQuantity(
                event.productId,
                event.isAdd
            )

            CartContract.Event.OnAllSelected -> updateAllSelected()
            is CartContract.Event.DeleteProduct -> deleteProduct(event.productId)
            CartContract.Event.CheckUserSession -> checkSessionStatus()
            CartContract.Event.ConfirmGift -> {}
            CartContract.Event.ConfirmPurchase -> { confirmPurchase() }
        }
    }

    private fun confirmPurchase() {
        viewModelScope.launch {
            cartUseCases.insertOrderUseCase()
        }
    }

    private fun checkSessionStatus() {
        viewModelScope.launch {
            val currentSession = checkSessionUseCase.invoke()

            if (currentSession) {
                setState { copy(isSessionCheckCompleted = true) }
                setEvent(CartContract.Event.LoadCartProducts)
            } else {
                setEffect { ToLogin }
            }
        }
    }

    private fun selectedTab(index: Int) {
        setState { copy(selectedTabIndex = index) }

        loadCartInProducts(index)
    }

    private fun loadCartInTobBar() {
        val tobBars = listOf(
            CartInTobBarModel("normal", "일반 배송"),
            CartInTobBarModel("today", "오늘 드림"),
            CartInTobBarModel("pickup", "픽업")
        )
        setState { copy(tobBars = tobBars) }
    }

    private fun deleteProduct(productId: String) {
        handleRepositoryCall(
            call = { cartUseCases.removeProductUseCase(productId) },
            onSuccess = { this }
        )
    }

    private fun updateAllSelected() {
        val allSelected = !viewState.value.allSelected
        handleRepositoryCall(
            call = { cartUseCases.updateAllProductSelectedUseCase(allSelected) },
            onSuccess = {
                copy(allSelected = allSelected)
            }
        )
    }


    private fun updateProductQuantity(productId: String, isAdd: Boolean) {
        if (isAdd) {
            handleRepositoryCall(
                call = { cartUseCases.increaseProductUseCase(productId = productId) },
                onSuccess = { this }
            )
        } else {
            handleRepositoryCall(
                call = { cartUseCases.decreaseProductUseCase(productId = productId) },
                onSuccess = { this }
            )
        }
    }

    private fun updateProductSelected(productId: String, isSelected: Boolean) {
        handleRepositoryCall(
            call = {
                cartUseCases.updateProductSelectedUseCase(
                    productId = productId,
                    isSelected = isSelected
                )
            },
            onSuccess = { this }
        )
    }

    private fun addToCart(productId: String) {
        handleRepositoryCall(
            call = { cartUseCases.addProductUseCase(productId) },
            onSuccess = {
                setEffect { CartContract.Effect.ShowToast(R.string.success_cart_in) }
                setEffect { CartContract.Effect.DismissCartPopup }
                this
            }
        )
    }

    private fun loadCartInProducts(deliveryType: Int = 0) {
        viewModelScope.launch {

            cartUseCases.cartItemUseCase(deliveryType)
                .map { cartItems -> cartItems.map { it.toDataStoreCartInProductsModel() } }
                .collect { products ->
                    val isAllSelected = products.isNotEmpty() && products.all { it.isSelected }
                    setState {
                        copy(
                            cartInProducts = products,
                            totalProductsCount = products.sumOf { it.quantity },
                            totalPrice = products.sumOf { it.quantity * it.discountPrice },
                            allSelected = isAllSelected
                        )
                    }
                }
        }
    }

    private fun loadPopupProductInfo(productId: String) {
        handleRepositoryCall(
            call = { cartUseCases.productInfoUseCase(productId = productId) },
            onSuccess = { productInfo ->
                copy(popupProductInfo = productInfo.toPopupProductInfoModel())
            }
        )
    }

    private fun <T> handleRepositoryCall(
        call: suspend () -> T,
        onSuccess: CartContract.State.(T) -> CartContract.State,
        onFinally: suspend (T) -> Unit = {}
    ) {
        viewModelScope.launch {
            setState { copy(loadingState = LoadingState.Loading) }
            try {
                val result = call()
                setState { onSuccess(result).copy(loadingState = LoadingState.Success) }
                onFinally(result)
            } catch (e: Exception) {
                setState { copy(loadingState = LoadingState.Error) }
                setEffect { CartContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }
}