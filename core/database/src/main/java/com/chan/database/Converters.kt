package com.chan.database

import androidx.room.TypeConverter
import com.chan.database.entity.ProductEntity
import com.chan.database.entity.order.OrderEntity.OrderItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun subCategoriesToJson(products: List<ProductEntity.Categories>): String =
        gson.toJson(products)

    @TypeConverter
    fun jsonToCategories(json: String): List<ProductEntity.Categories> =
        gson.fromJson(
            json,
            object :
                TypeToken<List<ProductEntity.Categories>>() {}.type
        )


    @TypeConverter
    fun fromListToString(list: List<String>?): String {
        return list?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun fromStringToList(value: String): List<String> {
        return if (value.isEmpty()) emptyList() else value.split(",")
    }


    @TypeConverter
    fun fromList(items: List<OrderItem>): String =
        Gson().toJson(items)

    @TypeConverter
    fun toList(json: String): List<OrderItem> =
        Gson().fromJson(json, object : TypeToken<List<OrderItem>>() {}.type)
}