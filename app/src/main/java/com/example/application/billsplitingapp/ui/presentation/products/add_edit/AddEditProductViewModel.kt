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
import com.example.application.billsplitingapp.domain.use_case.product.ChangeValueTextField
import com.example.application.billsplitingapp.domain.use_case.product.GetProduct
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
    private val getProduct: GetProduct,
    private val changeValueTextField: ChangeValueTextField
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _name = mutableStateOf<String>("")
    val name: State<String> = _name

    private val _value = mutableStateOf<TextFieldValue>(TextFieldValue("R$0,00", TextRange(6)))
    val value: State<TextFieldValue> = _value

    private val _amount = mutableStateOf<Int>(1)
    val amount: State<Int> = _amount

    private val _isEditing = mutableStateOf<Boolean>(false)
    val isEditing: State<Boolean> = _isEditing

    private var currentProductId: Int? = null

    val fullValue: Float
        get() = Formatter.currencyFormatFromString(_value.value.text) * _amount.value

    var billId: Int = -1

    init {
        savedStateHandle.get<Int>(Constants.BILL_ID)?.let { billId ->
            this.billId = billId
        }
        savedStateHandle.get<Int>("productId")?.let { productId ->
            if (productId != -1) {
                viewModelScope.launch {
                    getProduct(productId)?.also { product ->
                        currentProductId = productId
                        _name.value = product.name
                        val valueFormatted = Formatter.currencyFormatFromFloat(product.value)
                        _value.value = TextFieldValue(valueFormatted, TextRange(valueFormatted.length))
                        _amount.value = product.amount
                        _isEditing.value = true
                    }
                }
            }
        }
    }

    fun insertProduct() {
        viewModelScope.launch {
            insertProductUseCase(
                Product(
                    id = currentProductId ?: 0,
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
                val finalValue = changeValueTextField(value.value.text, event.value)
                _value.value = TextFieldValue(finalValue, TextRange(finalValue.length))
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