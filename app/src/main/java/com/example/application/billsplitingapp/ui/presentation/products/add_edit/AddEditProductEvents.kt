package com.example.application.billsplitingapp.ui.presentation.products.add_edit

import com.example.application.billsplitingapp.domain.model.Person

sealed class AddEditProductEvents {
    data class EnteredName(val name: String): AddEditProductEvents()
    data class EnteredValue(val value: String): AddEditProductEvents()
    data class ChangedAmount(val amount: Int): AddEditProductEvents()
    data class ToggledPersonSelection(val person: Person): AddEditProductEvents()
    object SaveProduct: AddEditProductEvents()
}
