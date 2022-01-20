package com.example.application.billsplitingapp.utils

object Formatter {
    fun currencyFormatFromFloat(value: Float): String  {
        var s = "R$" + String.format("%.2f", value)
        s = s.replace(".", ",")
        return s
    }

    fun currencyFormatFromString(value: String): Float {
        var p = value.trim()
        return if (p.isNotEmpty()) {
            p = p.removeRange(0, 2)
            p = p.replace(",", "")
            p = p.replace(".", "")
            p = p.substring(0, p.length - 2) + "." + p.substring(p.length - 2, p.length)
            p.toFloat()
        } else {
            0f
        }
    }
}