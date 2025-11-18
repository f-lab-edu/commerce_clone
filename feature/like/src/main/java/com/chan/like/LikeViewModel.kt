package com.chan.like

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.domain.usecase.GetLikedProductIdsUseCase
import com.chan.domain.usecase.GetLikedProductsUseCase
import com.chan.domain.usecase.ToggleLikeUseCase
import com.chan.like.LikeContract.Effect.Navigation.ToCartPopupRoute
import com.chan.like.LikeContract.Effect.Navigation.ToCartRoute
import com.chan.like.LikeContract.Effect.Navigation.ToProductDetail
import com.chan.like.LikeContract.Effect.Navigation.ToSearchRoute
import com.chan.like.mapper.toProductsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikeViewModel @Inject constructor(
    private val toggleLikeUseCase: ToggleLikeUseCase,
    private val getLikedProductIdsUseCase: GetLikedProductIdsUseCase,
    private val getLikedProductsUseCase: GetLikedProductsUseCase,
) : BaseViewModel<LikeContract.Event, LikeContract.State, LikeContract.Effect>() {

    init {
        observeLikes()
    }

    override fun setInitialState() = LikeContract.State()

    override fun handleEvent(event: LikeContract.Event) {
        when (event) {
            LikeContract.Event.TopBar.NavigateToCart -> setEffect { ToCartRoute }
            LikeContract.Event.TopBar.NavigateToSearch -> setEffect { ToSearchRoute }
            is LikeContract.Event.LikeAction.NavigateToProductDetail -> setEffect {
                ToProductDetail(
                    event.productId
                )
            }

            is LikeContract.Event.LikeAction.ShowCartPopup -> setEffect { ToCartPopupRoute(event.productId) }
            is LikeContract.Event.LikeAction.ToggleFavorite -> toggleFavorite(event.productId)
            LikeContract.Event.LoadFavorites -> loadFavorites()
        }
    }

    private fun observeLikes() {
        viewModelScope.launch {
            getLikedProductIdsUseCase.invoke()
                .collect { ids ->
                    setState { copy(likedProductIds = ids) }
                }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            val products = getLikedProductsUseCase.invoke().first().map { it.toProductsModel() }
            setState { copy(likeProducts = products) }
        }
    }

    private fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            toggleLikeUseCase.invoke(productId)
        }
    }
}