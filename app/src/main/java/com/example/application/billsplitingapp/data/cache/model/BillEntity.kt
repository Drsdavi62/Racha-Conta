package com.example.application.billsplitingapp.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.application.billsplitingapp.domain.model.Bill

@Entity
data class BillEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String,
    var value: Float,
    var date: String,

    var products: List<ProductEntity>,
    var people: List<PersonEntity>
)

fun BillEntity.toBill(): Bill {
    return Bill(
        id = id,
        name = name,
        value = value,
        date = date,
        products = products.map { it.toProduct() },
        people = people.map { it.toPerson() }
    )
}

fun Bill.toBillEntity(): BillEntity {
    return BillEntity(
        id = id,
        name = name,
        value = value,
        date = date,
        products = products.map { it.toProductEntity() },
        people = people.map { it.toPersonEntity() }
    )
}