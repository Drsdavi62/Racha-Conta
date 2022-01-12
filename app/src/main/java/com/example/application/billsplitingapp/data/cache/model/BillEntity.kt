package com.example.application.billsplitingapp.data.cache.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.application.billsplitingapp.domain.model.Bill

@Entity
data class BillEntity constructor(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String,
    var value: Float,
    var date: String,

    @Ignore var products: List<ProductEntity>? = null,
    @Ignore var people: List<PersonEntity>? = null
) {
    constructor(id: Int, name: String, value: Float, date: String): this(id, name,  value, date, null, null)
}

fun BillEntity.toBill(): Bill {
    return Bill(
        id = id,
        name = name,
        value = value,
        date = date,
        products = products?.map { it.toProduct() } ?: emptyList(),
        people = people?.map { it.toPerson() } ?: emptyList()
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