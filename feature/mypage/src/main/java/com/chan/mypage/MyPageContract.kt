package com.chan.mypage

import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState

class MyPageContract {

    sealed class Event: ViewEvent {
        object OnLogoutClicked : Event()
        object GetUserId: Event()
    }

    data class State(
        val userState: UserState = UserState()
    ) : ViewState

    data class UserState (
        val userName: String? = null,
        val userId: String? = null
    )

    sealed class Effect: ViewEffect {
        data class ShowToast(val message: String) : Effect()
        sealed class Navigation : Effect() {
            object ToHome : Navigation()
        }
    }
}