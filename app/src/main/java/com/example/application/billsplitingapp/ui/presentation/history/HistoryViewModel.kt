package com.example.application.billsplitingapp.ui.presentation.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.use_case.GetBills
import com.example.application.billsplitingapp.models.BillModel
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    val getBills: GetBills
) : ViewModel() {

    private val _state = mutableStateOf(HistoryListState())
    val state: State<HistoryListState> = _state

    init {
        getBillsList()
    }

    private fun getBillsList() {
        getBills().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = HistoryListState(bills = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = HistoryListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = HistoryListState(error = result.message ?: "Unknown Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteBills(ids: List<Int>) {
//        CoroutineScope(Dispatchers.IO).launch {
//            billRepository.deleteMultipleBills(ids)
//            productRepository.deleteByMultipleBills(ids)
//            personRepository.deleteByMultipleBills(ids)
//        }
    }


    fun setUp(list: List<BillModel>) {
//        CoroutineScope(Dispatchers.IO).launch {
//            list.forEach { bill ->
//                val productList = productRepository.getRawList(bill.id)
//                billRepository.setBillValue(bill.id, productList.map { it.price }.sum())
//            }
//        }
    }

    fun getRelationList(billList: List<Bill>): List<List<PersonModel>> {
        return emptyList()
    }

}