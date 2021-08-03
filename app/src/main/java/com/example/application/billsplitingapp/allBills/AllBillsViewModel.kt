package com.example.application.billsplitingapp.allBills

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.application.billsplitingapp.database.bill.BillRepository
import com.example.application.billsplitingapp.database.person.PersonRepository
import com.example.application.billsplitingapp.database.product.ProductRepository
import com.example.application.billsplitingapp.models.BillModel
import com.example.application.billsplitingapp.models.PersonModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class AllBillsViewModel(application: Application) : AndroidViewModel(application) {

    private val billRepository = BillRepository(application)
    private val productRepository = ProductRepository(application)
    private val personRepository = PersonRepository(application)
    var list: LiveData<List<BillModel>> = billRepository.getList()

    fun insertBill(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            billRepository.insertBill(
                name,
                DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().time)
            )
        }
    }

    fun editBill(id: Int, name: String) {
        CoroutineScope(Dispatchers.IO).launch { billRepository.editBill(id, name) }
    }

    fun deleteBill(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            billRepository.deleteBill(id)
            productRepository.deleteByBill(id)
            personRepository.deleteByBill(id)
        }

    }

    fun setUp(list: List<BillModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            list.forEach { bill ->
                val productList = productRepository.getRawList(bill.id)
                billRepository.setBillValue(bill.id, productList.map { it.price }.sum())
            }
        }
    }

    fun getRelationList(billList: List<BillModel>): List<List<PersonModel>> = runBlocking {
        val finalList: MutableList<List<PersonModel>> = ArrayList()
        billList.forEach { bill ->
            finalList.add(personRepository.getRawList(bill.id))
        }
        return@runBlocking finalList
    }

}