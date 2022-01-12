package com.example.application.billsplitingapp.data.cache.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.application.billsplitingapp.domain.model.Product

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = BillEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("billId"),
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    )
)
data class ProductEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var billId: Int,

    var name: String,
    var value: Float,
    var amount: Int,

    @Ignore
    var people: List<PersonEntity>? = null
) {
    constructor(id: Int, billId: Int, name: String, value: Float, amount: Int): this(id, billId, name, value, amount, null)
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id = id,
        billId = billId,
        name = name,
        value = value,
        amount = amount,
        people = people?.map { it.toPerson() } ?: emptyList()
    )
}

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        billId = billId,
        name = name,
        value = value,
        amount = amount,
        people = people.map { it.toPersonEntity() }
    )
}