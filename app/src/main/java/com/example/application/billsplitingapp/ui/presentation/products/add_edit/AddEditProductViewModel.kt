package com.example.application.billsplitingapp.ui.presentation.products.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.domain.use_case.product.InsertProduct
import com.example.application.billsplitingapp.domain.use_case.bill.UpdateBillValue
import com.example.application.billsplitingapp.utils.Constants
import com.example.application.billsplitingapp.utils.Formatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val insertProductUseCase: InsertProduct,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _name = mutableStateOf<String>("")
    val name: State<String> = _name

    private val _value = mutableStateOf<TextFieldValue>(TextFieldValue("R$0,00", TextRange(6)))
    val value: State<TextFieldValue> = _value

    private val _amount = mutableStateOf<Int>(1)
    val amount: State<Int> = _amount

    val fullValue: Float
        get() = Formatter.currencyFormatFromString(_value.value.text) * _amount.value

    var billId: Int = -1

    init {
        savedStateHandle.get<Int>(Constants.BILL_ID)?.let { billId ->
            this.billId = billId
        }
    }

    fun insertProduct() {
        viewModelScope.launch {
            insertProductUseCase(
                Product(
                    billId = billId,
                    name = _name.value,
                    value = Formatter.currencyFormatFromString(_value.value.text),
                    amount = _amount.value,
                    people = emptyList()
                )
            )
            _eventFlow.emit(UIEvents.SuccessSavingProduct)
        }
    }

    fun onEvent(event: AddEditProductEvents) {
        when (event) {
            is AddEditProductEvents.EnteredName -> {
                _name.value = event.name
            }
            is AddEditProductEvents.EnteredValue -> {
                var previous = value.value.text
                var future = event.value

                future = future.replace(",", "")
                if (event.value.length > previous.length) {
                    if (future[2] == '0') {
                        future = future.removeRange(2, 3)
                    }
                } else if (event.value.length < previous.length) {
                    if (previous.length <= 6) {
                        future = future.substring(0, 2) + "0" + future.substring(2, future.length)
                    }
                }
                future = future.substring(0, future.length - 2) + "," + future.subSequence(
                    future.length - 2,
                    future.length
                )
                _value.value = TextFieldValue(future, TextRange(future.length))
            }
            is AddEditProductEvents.ChangedAmount -> {
                _amount.value = event.amount
            }
            AddEditProductEvents.SaveProduct -> {
                insertProduct()
            }
        }
    }

    sealed class UIEvents {
        object SuccessSavingProduct: UIEvents()
        object ErrorSavingProduct: UIEvents()
    }
}