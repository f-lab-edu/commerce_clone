package com.chan.cart

import app.cash.turbine.test
import com.chan.auth.domain.usecase.CheckSessionUseCase
import com.chan.cart.domain.usecase.CartUseCases
import com.chan.cart.fixture.CartItemFixture
import com.chan.cart.proto.CartItem
import com.chan.cart.rule.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class CartViewModelTest: CartViewModelTestBase() {

    @Test
    fun `init - viewModel이 생성되면 장바구니 상품을 정상적으로 불러온다`() = runTest {
        //Given
        val mockCartItems = CartItemFixture.createCarItems()

        //상품 목록
        mockSetup.setupCartItems(mockCartItems)
        //세션이 유효하다고 가정
        mockSetup.setupSessionValid(true)

        //When
        createCartViewModel()
        advanceUntilIdle()

        //Then
        viewModel.viewState.test {
            val state = awaitItem()

            assertEquals(2, state.cartInProducts.size)
            assertEquals("p1", state.cartInProducts[0].productId)
            assertEquals(2, state.cartInProducts[0].quantity)

            assertEquals(3, state.totalProductsCount)
            assertEquals(31000, state.totalPrice)
            assertTrue(state.allSelected)
        }
    }


}
