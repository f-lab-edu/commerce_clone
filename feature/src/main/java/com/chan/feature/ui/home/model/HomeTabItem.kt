package com.chan.feature.ui.home.model

sealed class HomeTabItem(val title: String) {
    object Home : HomeTabItem("홈")
    object RecommendToday : HomeTabItem("오늘의 추천")
    object Plan : HomeTabItem("기획전")
    object Event : HomeTabItem("이벤트")
    object SaleNow : HomeTabItem("세일중")
    object NewArrival : HomeTabItem("신규상품")
    object Ranking : HomeTabItem("랭킹")
    object Sale : HomeTabItem("세일")
    object LuxeEdit : HomeTabItem("LUXE EDIT")

    companion object {
        fun tabList(): List<HomeTabItem> = listOf(
            Home, RecommendToday, Plan, Event, SaleNow,
            NewArrival, Ranking, Sale, LuxeEdit
        )
    }
}