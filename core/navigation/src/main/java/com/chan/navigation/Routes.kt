package com.chan.navigation

import android.net.Uri

enum class Routes(val route: String) {
    HOME("home"),
    HOME_BANNER_WEB_VIEW("homeBannerWebView/{url}"),
    CATEGORY("category"),
    CATEGORY_DETAIL("categoryDetail/{categoryId}"),
    PRODUCT_DETAIL("productDetail/{productId}"),
    HISTORY("history"),
    MYPAGE("mypage"),
    LOGIN("login"),
    SEARCH("search"),
    CART("cart"),
    CART_POPUP("cart/{productId}");

    fun homeBannerWebViewRoute(url: String) =
        "homeBannerWebView/${Uri.encode(url)}"

    fun categoryDetailRoute(categoryId: String) =
        "categoryDetail/$categoryId"

    fun productDetailRoute(productId: String) =
        "productDetail/$productId"

    fun cartPopUpRoute(productId: String) =
        "cart/$productId"
}

fun createLoginRoute(redirect: String = ""): String {
    return "${Routes.LOGIN.route}?redirect=$redirect"
}

val routesRequiringAuth = setOf(
    Routes.CART.route,
    Routes.CART_POPUP.route,
    Routes.MYPAGE.route
)
