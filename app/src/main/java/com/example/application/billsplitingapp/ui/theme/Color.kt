package com.example.application.billsplitingapp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Blue400 = Color(0xFF66ddff)
val Blue500 = Color(0xFF00abef)
val Blue600 = Color(0xFF009de0)
val Blue700 = Color(0xFF008acd)
val Blue900 = Color(0xFF005997)

val White = Color(0xFFFFFFFF)
val DarkGray = Color(0xFF0E0E0E)

val Orange500 = Color(0xFFff510c)
val Orange400 = Color(0xFFff6d38)

val Teal200 = Color(0xFF00efbb)

val DisabledGray = Color(0xFFADADAD)

val Colors.moneyGreen: Color
    get() = if (isLight) Color(0xFF316B3E) else Color(0xFF27CC4C)

val Colors.invisibleCardColor: Color
    get() = if (isLight) White else Color(0xFF2B2B2B)