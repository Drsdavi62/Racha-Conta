package com.example.application.billsplitingapp.domain.model

data class Product(
    var id: Int = 0,
    var billId: Int,
    var name: String,
    var value: Float,
    var amount: Int,
    var people: List<Person>
) {
    val fullValue = value * amount
}