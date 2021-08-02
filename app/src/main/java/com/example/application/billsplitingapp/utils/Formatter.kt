package com.example.application.billsplitingapp.utils

class Formatter {
    companion object{
        fun currencyFormat(value : Float) = "R$ " + String.format("%.2f", value)
    }
}