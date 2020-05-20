package com.example.application.billsplitingapp.allBills

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.application.billsplitingapp.database.bill.BillRepository
import com.example.application.billsplitingapp.models.BillModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

class AllBillsViewModel(application: Application) : AndroidViewModel(application) {

    private val billRepository = BillRepository(application)
    var list : LiveData<List<BillModel>>

    init{
        list = billRepository.getList()
    }

    fun insertBill(name : String){
        CoroutineScope(Dispatchers.IO).launch {
            billRepository.insertBill(name, DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().time))
        }
    }

    fun editBill(id : Int, name: String){
        CoroutineScope(Dispatchers.IO).launch { billRepository.editBill(id, name) }
    }

    fun deleteBill(id : Int){
        CoroutineScope(Dispatchers.IO).launch { billRepository.deleteBill(id) }
    }

}