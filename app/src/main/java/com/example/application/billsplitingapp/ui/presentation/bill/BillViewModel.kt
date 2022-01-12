package com.example.application.billsplitingapp.ui.presentation.bill

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.billsplitingapp.domain.model.Bill
import com.example.application.billsplitingapp.domain.model.Product
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
                _bill.value = bill.value?.copy(products = listOf(
                    Product(
                        id = 0,
                        billId = bill.value!!.id,
                        name = "Batata frita",
                        value = 30.0f,
                        amount = 1,
                        people = listOf()
                    ),
                    Product(
                        id = 1,
                        billId = bill.value!!.id,
                        name = "Hamburguer",
                        value = 40.0f,
                        amount = 2,
                        people = listOf()
                    ),
                    Product(
                        id = 2,
                        billId = bill.value!!.id,
                        name = "Refri",
                        value = 10.0f,
                        amount = 1,
                        people = listOf()
                    ),
                    Product(
                        id = 3,
                        billId = bill.value!!.id,
                        name = "Sobremesa",
                        value = 20.0f,
                        amount = 1,
                        people = listOf()
                    ),
                ))
            }
        }
    }
}