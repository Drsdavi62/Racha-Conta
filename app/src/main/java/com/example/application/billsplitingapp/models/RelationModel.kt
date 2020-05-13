package com.example.application.billsplitingapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RelationModel(
    var productId: Integer,
    var personId: Integer,
    var value: Float
) {
    @PrimaryKey(autoGenerate = true)
    lateinit var id : Integer
}