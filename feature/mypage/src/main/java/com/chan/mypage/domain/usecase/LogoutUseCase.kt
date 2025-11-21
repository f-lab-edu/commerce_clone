package com.chan.mypage.domain.usecase

import com.chan.auth.domain.AuthRepository
import com.chan.database.datastore.CartDataStoreManager
import com.chan.database.datastore.LikeDataStoreManager
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {
        authRepository.logout()
        CartDataStoreManager.clearAll()
        LikeDataStoreManager.clearAll()
    }
}