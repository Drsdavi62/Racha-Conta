package com.example.application.billsplitingapp.ui.presentation.people.add_edit

import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.domain.model.Product

sealed class AddEditPersonEvents {
    data class EnteredName(val name: String): AddEditPersonEvents()
    data class ToggledProductSelection(val product: Product): AddEditPersonEvents()
    object ToggleAllProducts: AddEditPersonEvents()
    object SavePerson: AddEditPersonEvents()
}
