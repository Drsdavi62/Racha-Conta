package com.example.application.billsplitingapp.data.cache.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.application.billsplitingapp.domain.model.Person

data class FullPersonEntity(
    @Embedded
    val person: PersonEntity,
    @Relation(
        parentColumn = "id",
        entity = ProductEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = ProductWithPeopleEntity::class,
            parentColumn = "personId",
            entityColumn = "productId"
        )
    )
    var products: List<ProductEntity>
)

fun FullPersonEntity.toPerson(): Person {
    val p = person.toPerson()
    p.products = products.map { it.toProduct() }
    return p
}