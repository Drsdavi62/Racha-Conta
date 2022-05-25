package com.example.application.billsplitingapp.data.cache

import androidx.room.TypeConverter
import com.example.application.billsplitingapp.data.cache.model.PersonEntity
import com.example.application.billsplitingapp.data.cache.model.ProductEntity
import com.google.gson.Gson

class BillTypeConverters {
    @TypeConverter
    fun personListToJson(value: List<PersonEntity>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToPersonList(value: String) = Gson().fromJson(value, Array<PersonEntity>::class.java).toList()

    @TypeConverter
    fun productListToJson(value: List<ProductEntity>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToProductList(value: String) = Gson().fromJson(value, Array<ProductEntity>::class.java).toList()
}