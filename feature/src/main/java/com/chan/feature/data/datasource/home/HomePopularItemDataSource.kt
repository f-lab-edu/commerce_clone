package com.chan.feature.data.datasource.home

import android.content.Context
import com.chan.feature.data.entity.HomePopularItemEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HomePopularItemDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getDummyPopularItems(): List<HomePopularItemEntity> {
        return try {
            val jsonString = context.assets.open("home_popular_item.json")
                .bufferedReader()
                .use { it.readText() }

            val type = object : TypeToken<List<HomePopularItemEntity>>() {}.type
            Gson().fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}