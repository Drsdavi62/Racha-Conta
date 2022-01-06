package com.example.application.billsplitingapp.data.cache

import androidx.room.*
import com.example.application.billsplitingapp.data.cache.model.PersonEntity

@Dao
interface PersonDao {

    @Query("SELECT * FROM personentity WHERE billId = :billId")
    suspend fun getPeopleFromBill(billId: Int): List<PersonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(personEntity: PersonEntity)

    @Delete
    suspend fun deletePerson(personEntity: PersonEntity)
}