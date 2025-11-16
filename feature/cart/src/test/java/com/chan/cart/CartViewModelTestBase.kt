package com.chan.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.chan.auth.domain.usecase.CheckSessionUseCase
import com.chan.cart.domain.usecase.CartUseCases
import com.chan.cart.rule.MainDispatcherRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalCoroutinesApi
abstract class CartViewModelTestBase {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    protected val checkSessionUseCase: CheckSessionUseCase = mockk(relaxed = true)
    protected val cartUseCases: CartUseCases = mockk(relaxed = true)

    protected lateinit var viewModel: CartViewModel
    protected lateinit var mockSetup: MockSetup


    @Before
    fun baseSetUp() {
        mockSetup = MockSetup(cartUseCases, checkSessionUseCase)
        mockSetup.setupCartItems(emptyList())
    }

    protected fun createCartViewModel() {
        viewModel = CartViewModel(checkSessionUseCase, cartUseCases)
    }
}