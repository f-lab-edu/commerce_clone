package com.chan.cart

import com.chan.auth.domain.usecase.CheckSessionUseCase
import com.chan.cart.domain.usecase.CartUseCases
import com.chan.cart.proto.CartItem
import io.mockk.coEvery
import kotlinx.coroutines.flow.flowOf

/**
 * Mock 설정 로직 분리 (Single Responsibility)
 * 책임: Mock 객체만 설정
 */
class MockSetup(
    private val cartUseCases: CartUseCases,
    private val checkSessionUseCase: CheckSessionUseCase
) {

    fun setupCartItems(items: List<CartItem> = emptyList()) {
        coEvery { cartUseCases.cartItemUseCase(any()) } returns flowOf(items)
    }

    fun setupSessionValid(isValid: Boolean = true) {
        coEvery { checkSessionUseCase.invoke() } returns isValid
    }

    fun setupError(exception: Exception = Exception("Test error")) {
        coEvery { cartUseCases.cartItemUseCase(any()) } throws exception
    }
}