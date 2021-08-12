package com.example.application.billsplitingapp.ui.presentation.history

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

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val billRepository = BillRepository(application)
    private val productRepository = ProductRepository(application)
    private val personRepository = PersonRepository(application)

    var list: LiveData<List<BillModel>> = billRepository.getList()

    fun deleteBills(ids: List<Int>) {
        CoroutineScope(Dispatchers.IO).launch {
            billRepository.deleteMultipleBills(ids)
            productRepository.deleteByMultipleBills(ids)
            personRepository.deleteByMultipleBills(ids)
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