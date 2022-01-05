package com.example.application.billsplitingapp.ui.presentation.homeFragment

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.application.billsplitingapp.BillSplitApp
import com.example.application.billsplitingapp.database.bill.BillRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext val application: Context
) : ViewModel() {

    private val billRepository = BillRepository(application)

    fun insertBill(name: String): Int = runBlocking {
        return@runBlocking billRepository.insertBill(
            name,
            DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().time)
        ).toInt()
    }
}