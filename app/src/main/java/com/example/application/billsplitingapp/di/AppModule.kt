package com.example.application.billsplitingapp.di

import android.app.Application
import androidx.room.Room
import com.example.application.billsplitingapp.data.cache.BillDatabase
import com.example.application.billsplitingapp.data.repository.BillRepositoryImpl
import com.example.application.billsplitingapp.domain.repository.BillRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBillDatabase(app: Application): BillDatabase {
        return Room.databaseBuilder(
            app,
            BillDatabase::class.java,
            BillDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBillRepository(db: BillDatabase): BillRepository {
        return BillRepositoryImpl(dao = db.billDao)
    }
}