package com.chan.cart.domain.usecase

import com.chan.cart.domain.CartRepository
import javax.inject.Inject

data class CartUseCases @Inject constructor(
    val productInfoUseCase: ProductInfoUseCase,
    val cartItemUseCase: CartItemUseCase,
    val addProductUseCase: AddProductUseCase,
    val removeProductUseCase: RemoveProductUseCase,
    val updateProductSelectedUseCase: UpdateProductSelectedUseCase,
    val updateAllProductSelectedUseCase: UpdateAllProductSelectedUseCase,
    val increaseProductUseCase: IncreaseProductUseCase,
    val decreaseProductUseCase: DecreaseProductUseCase,
    val insertOrderUseCase: InsertOrderUseCase
)

class ProductInfoUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: String) = cartRepository.getProductInfo(productId)
}

class CartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(deliveryType: Int) = cartRepository.getCartItems(deliveryType)
}

class AddProductUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: String) = cartRepository.addProductToCart(productId)
}

class RemoveProductUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: String) = cartRepository.removeProductFromCart(productId)
}

class UpdateProductSelectedUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: String, isSelected: Boolean) =
        cartRepository.updateProductSelected(productId, isSelected)
}

class UpdateAllProductSelectedUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(isSelected: Boolean) =
        cartRepository.updateAllProductsSelected(isSelected)
}

class IncreaseProductUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: String) =
        cartRepository.increaseProductQuantity(productId)
}

class DecreaseProductUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: String) =
        cartRepository.decreaseProductQuantity(productId)
}

class InsertOrderUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke() {
        cartRepository.insertOrder()
    }
}