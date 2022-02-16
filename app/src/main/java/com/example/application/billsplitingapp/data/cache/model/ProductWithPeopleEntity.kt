package com.example.application.billsplitingapp.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["productId", "personId"])
data class ProductWithPeopleEntity(
    val productId: Int,
    val personId: Int
)