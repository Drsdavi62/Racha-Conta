package com.example.application.billsplitingapp.ui.presentation.people.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.domain.use_case.people.GetPersonById
import com.example.application.billsplitingapp.domain.use_case.people.InsertPerson
import com.example.application.billsplitingapp.domain.use_case.product.GetProductListFromBill
import com.example.application.billsplitingapp.ui.presentation.products.add_edit.toggle
import com.example.application.billsplitingapp.utils.Constants
import com.example.application.billsplitingapp.utils.addAllDistinct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val insertPerson: InsertPerson,
    private val getPersonById: GetPersonById,
    private val getProductListFromBill: GetProductListFromBill
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _name = mutableStateOf<String>("")
    val name: State<String> = _name

    private val _isEditing = mutableStateOf<Boolean>(false)
    val isEditing: State<Boolean> = _isEditing

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    val selectedProducts = mutableStateListOf<Product>()

    private var currentPersonId: Int? = null

    var billId: Int = -1

    init {
        savedStateHandle.get<Int>(Constants.BILL_ID)?.let { billId ->
            this.billId = billId
        }
        savedStateHandle.get<Int>("personId")?.let { personId ->
            viewModelScope.launch {
                if (personId != -1) {
                    getPersonById(personId)?.also { person ->
                        currentPersonId = personId
                        _name.value = person.name
                        selectedProducts.addAll(person.products)
                        _isEditing.value = true
                    }
                }
                _products.value = getProductListFromBill(billId)
            }
        }
    }

    fun insertPerson() {
        viewModelScope.launch {
            insertPerson(
                Person(
                    id = currentPersonId ?: 0,
                    billId = billId,
                    name = _name.value,
                    products = selectedProducts
                )
            )
            _eventFlow.emit(UIEvents.SuccessSavingPerson)
        }
    }

    fun onEvent(event: AddEditPersonEvents) {
        when (event) {
            is AddEditPersonEvents.EnteredName -> {
                _name.value = event.name
            }
            AddEditPersonEvents.SavePerson -> {
                insertPerson()
            }
            is AddEditPersonEvents.ToggledProductSelection -> {
                selectedProducts.toggle(event.product)
            }
            AddEditPersonEvents.ToggleAllProducts -> {
                if (selectedProducts.size == products.value.size) {
                    selectedProducts.clear()
                } else {
                    selectedProducts.addAllDistinct(products.value)
                }
            }
        }
    }

    sealed class UIEvents {
        object SuccessSavingPerson : UIEvents()
        object ErrorSavingPerson : UIEvents()
    }
}
