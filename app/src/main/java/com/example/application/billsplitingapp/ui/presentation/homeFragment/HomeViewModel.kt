package com.example.application.billsplitingapp.ui.presentation.homeFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.application.billsplitingapp.database.bill.BillRepository
import kotlinx.coroutines.runBlocking
import java.text.DateFormat
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val billRepository = BillRepository(application)

    fun insertBill(name: String): Int = runBlocking {
        return@runBlocking billRepository.insertBill(
            name,
            DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().time)
        ).toInt()
    }
}