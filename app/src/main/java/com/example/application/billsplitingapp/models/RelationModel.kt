package com.example.application.billsplitingapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RelationModel(
    var productId: Int,
    var personId: Int,
    var value: Float
) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}