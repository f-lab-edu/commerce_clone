package com.chan.home.model

import androidx.annotation.StringRes
import com.chan.home.R

sealed class HomeTabItem(@StringRes val titleResId: Int) {
    object Home : HomeTabItem(R.string.home_tab_home)
//    object RecommendToday : HomeTabItem(R.string.home_tab_recommend_today)
//    object Plan : HomeTabItem(R.string.home_tab_plan)
//    object Event : HomeTabItem(R.string.home_tab_event)
//    object SaleNow : HomeTabItem(R.string.home_tab_sale_now)
//    object NewArrival : HomeTabItem(R.string.home_tab_new_arrival)
    object Ranking : HomeTabItem(R.string.home_tab_ranking)
//    object Sale : HomeTabItem(R.string.home_tab_sale)
//    object LuxeEdit : HomeTabItem(R.string.home_tab_luxe_edit)

    companion object {
        fun tabList(): List<HomeTabItem> = listOf(
//            Home, RecommendToday, Plan, Event, SaleNow,
//            NewArrival, Ranking, Sale, LuxeEdit
            Home, Ranking
        )
    }
}