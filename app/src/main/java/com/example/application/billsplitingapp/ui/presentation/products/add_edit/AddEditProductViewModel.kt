package com.example.application.billsplitingapp.ui.presentation.products.add_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.domain.use_case.InsertProduct
import com.example.application.billsplitingapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val insertProductUseCase: InsertProduct
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow = _eventFlow.asSharedFlow()

    var billId: Int = -1

    init {
        savedStateHandle.get<Int>(Constants.BILL_ID)?.let { billId ->
            this.billId = billId
        }
    }

    fun insertProduct(name: String, amount: Int, value: Float, people: List<Person>) {
        viewModelScope.launch {
            insertProductUseCase(
                Product(
                    billId = billId,
                    name = name,
                    value = value,
                    amount = amount,
                    people = people
                )
            )
            _eventFlow.emit("foi")
        }
    }

}