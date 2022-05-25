package com.example.application.billsplitingapp

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BillSplitApp: Application() {

    val isDark = mutableStateOf(false)

    fun toggleDarkTheme() {
        isDark.value = !isDark.value
    }
}