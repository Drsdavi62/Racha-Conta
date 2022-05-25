package com.example.application.billsplitingapp.domain.model

data class Person(
    var id: Int = 0,
    var billId: Int,
    var name: String,
    var products: List<Product>
) {
    var value: Float = 0f
}