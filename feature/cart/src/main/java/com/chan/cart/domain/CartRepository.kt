package com.chan.cart.domain

import com.chan.cart.proto.CartItem
import com.chan.domain.ProductsVO
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    /**
     * 장바구니 팝업 상품의 상세 정보 조회
     */
    suspend fun getProductInfo(productId: String): ProductsVO

    /**
     * 장바구니 상품 목록
     */
    fun getCartItems(deliveryType: Int): Flow<List<CartItem>>

    /**
     * 장바구니 상품 추가
     */
    suspend fun addProductToCart(productId: String)

    /**
     * 장바구니에서 특정 상품을 제거
     */
    suspend fun removeProductFromCart(productId: String)

    /**
     * 상품 선택 상태 업데이트
     */
    suspend fun updateProductSelected(productId: String, isSelected: Boolean)

    /**
     * 상품 수량 + 1
     */
    suspend fun increaseProductQuantity(productId: String)

    /**
     * 상품 수량 - 1
     */
    suspend fun decreaseProductQuantity(productId: String)

    /**
     * 상품 상태 전체 업데이트 (선택/해제)
     */
    suspend fun updateAllProductsSelected(isSelected: Boolean)

    suspend fun clearCart()

    suspend fun insertOrder()
}
