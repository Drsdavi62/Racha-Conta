package com.example.application.billsplitingapp

import android.app.Application
import androidx.compose.runtime.mutableStateOf

class BillSplitApp: Application() {

    val isDark = mutableStateOf(false)

    fun toggleDarkTheme() {
        isDark.value = !isDark.value
    }
}