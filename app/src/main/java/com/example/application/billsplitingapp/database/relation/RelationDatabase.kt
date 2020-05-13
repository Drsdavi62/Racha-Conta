package com.example.application.billsplitingapp.database.relation

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.application.billsplitingapp.database.product.ProductDatabase
import com.example.application.billsplitingapp.models.RelationModel

@Database(entities = [RelationModel::class], version = 3, exportSchema = false)
abstract class RelationDatabase : RoomDatabase() {
    abstract fun relationDao() : RelationDao

    companion object{
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: RelationDatabase? = null

        fun getDatabase(context: Context): RelationDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RelationDatabase::class.java,
                    "Relation-database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}