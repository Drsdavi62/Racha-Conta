package com.example.application.billsplitingapp.ui.presentation.bill

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.use_case.GetBill
import com.example.application.billsplitingapp.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBill: GetBill
) : ViewModel() {

    private val _bill = mutableStateOf<Bill?>(null)
    val bill: State<Bill?> = _bill

    init {
        savedStateHandle.get<Int>("billId")?.let { billId ->
            viewModelScope.launch {
                _bill.value = getBill(billId)
            }
        }
    }
}