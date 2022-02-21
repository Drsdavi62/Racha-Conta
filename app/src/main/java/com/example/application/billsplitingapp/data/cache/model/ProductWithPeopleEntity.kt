package com.example.application.billsplitingapp.data.cache.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["productId", "personId"],
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("productId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("personId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class ProductWithPeopleEntity(
    val productId: Int,
    val personId: Int
)