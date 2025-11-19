package com.chan.like

import com.chan.android.LoadingState
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.android.model.ProductsModel

class LikeContract {

    sealed class Event : ViewEvent {
        object LoadFavorites: Event()

        sealed class TopBar : Event() {
            object NavigateToSearch : TopBar()
            object NavigateToCart : TopBar()
        }

        sealed class LikeAction : Event() {
            data class ToggleFavorite(val productId: String) : LikeAction()
            data class NavigateToProductDetail(val productId: String) : LikeAction()
            data class ShowCartPopup(val productId: String) : LikeAction()
        }
    }

    data class State(
        //좋아요 상품 목록
        val likeProducts: List<ProductsModel> = emptyList(),
        //좋아요 상품 Id
        val likedProductIds: Set<String> = emptySet(),
        val loadingState: LoadingState = LoadingState.Idle
    ) : ViewState

    sealed class Effect : ViewEffect {
        sealed class Navigation : Effect() {
            object ToCartRoute : Navigation()
            object ToSearchRoute : Navigation()
            data class ToCartPopupRoute(val productId: String) : Navigation()
            data class ToProductDetail(val productId: String) : Navigation()
        }
    }
}