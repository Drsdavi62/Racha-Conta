package com.example.application.billsplitingapp.utils

object Formatter {
    fun currencyFormat(value: Float) = "R$ " + String.format("%.2f", value)
}