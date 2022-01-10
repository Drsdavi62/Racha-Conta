package com.example.application.billsplitingapp.ui.presentation.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.use_case.DeleteBill
import com.example.application.billsplitingapp.domain.use_case.GetBills
import com.example.application.billsplitingapp.models.BillModel
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    val getBills: GetBills,
    val deleteBill: DeleteBill
) : ViewModel() {

    private val _state = mutableStateOf(HistoryListState())
    val state: State<HistoryListState> = _state

    var job: Job? = null

    init {
        getBillsList()
    }

    private fun getBillsList() {
        job?.cancel()
        job = getBills().onEach { bills ->
            _state.value = HistoryListState(bills = bills)
        }.launchIn(viewModelScope)
    }

    fun deleteBills(bills: List<Bill>) {
        viewModelScope.launch {
            bills.forEach {
                deleteBill(it)
            }
        }
    }
}