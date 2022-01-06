package com.example.application.billsplitingapp.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.application.billsplitingapp.data.cache.model.BillEntity
import com.example.application.billsplitingapp.data.cache.model.PersonEntity
import com.example.application.billsplitingapp.data.cache.model.ProductEntity
import com.google.gson.Gson

@Database(entities = [BillEntity::class, PersonEntity::class, ProductEntity::class], version = 1)
@TypeConverters(BillTypeConverters::class)
abstract class BillDatabase: RoomDatabase() {

    abstract val billDao: BillDao
    abstract val personDao: PersonDao
    abstract val productDao: ProductDao

    companion object {
        const val DATABASE_NAME = "bill_db"
    }
}