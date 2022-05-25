package com.example.application.billsplitingapp.data.cache.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.application.billsplitingapp.domain.model.Person

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = BillEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("billId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
    ]
)
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var billId: Int,

    var name: String,
)

fun PersonEntity.toPerson(): Person {
    return Person(
        id = id,
        billId = billId,
        name = name,
        products = emptyList()
    )
}

fun Person.toPersonEntity(): PersonEntity {
    return PersonEntity(
        id = id,
        billId = billId,
        name = name
    )
}

