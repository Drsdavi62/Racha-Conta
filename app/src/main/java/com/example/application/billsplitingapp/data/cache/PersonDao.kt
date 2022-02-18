package com.example.application.billsplitingapp.data.cache

import androidx.room.*
import com.example.application.billsplitingapp.data.cache.model.PersonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("SELECT * FROM personentity WHERE billId = :billId")
    fun getPeopleFromBill(billId: Int): Flow<List<PersonEntity>>

    @Query("SELECT * FROM personentity WHERE billId = :billId")
    suspend fun getStaticPeopleFromBill(billId: Int): List<PersonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(personEntity: PersonEntity): Long

    @Delete
    suspend fun deletePerson(personEntity: PersonEntity)
}