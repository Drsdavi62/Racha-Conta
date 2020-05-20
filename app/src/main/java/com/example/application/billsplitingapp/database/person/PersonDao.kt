package com.example.application.billsplitingapp.database.person

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.application.billsplitingapp.models.PersonModel

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(person: PersonModel) : Long

    @Query("SELECT * FROM PersonModel where billId = :billId")
    fun getPersonList(billId : Int) : LiveData<List<PersonModel>>

    @Query("SELECT * FROM PersonModel where billId = :billId")
    suspend fun getRawPersonList(billId : Int) : List<PersonModel>

    @Query("DELETE FROM PersonModel WHERE id = :id")
    suspend fun deletePerson(id: Integer)

    @Query("UPDATE PersonModel SET name = :name WHERE id = :id")
    suspend fun editPersonName(id: Integer, name: String)

    @Query("UPDATE PersonModel SET value =:value WHERE id =:id")
    suspend fun setValue(value: Float, id: Integer)

    @Query("UPDATE PersonModel SET value = value - :value WHERE id =:id")
    suspend fun subtractToPersonValue(value: Float, id: Integer)

    @Query("SELECT * FROM PersonModel WHERE id =:id")
    suspend fun getPerson(id: Int) : PersonModel
}