package com.example.application.billsplitingapp.data.cache.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.application.billsplitingapp.domain.model.Product

data class FullProductEntity (
    @Embedded
    var productEntity: ProductEntity,
    @Relation(
        parentColumn = "id",
        entity = PersonEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = ProductWithPeopleEntity::class,
            parentColumn = "productId",
            entityColumn = "personId"
        )
    )
    var people: List<PersonEntity>
)

fun FullProductEntity.toProduct(): Product {
    val p = productEntity.toProduct()
    p.people = people.map { it.toPerson() }
    return p
}