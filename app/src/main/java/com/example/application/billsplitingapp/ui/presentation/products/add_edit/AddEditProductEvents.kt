package com.example.application.billsplitingapp.ui.presentation.products.add_edit

sealed class AddEditProductEvents {
    data class EnteredName(val name: String): AddEditProductEvents()
    data class EnteredValue(val value: String): AddEditProductEvents()
    data class ChangedAmount(val amount: Int): AddEditProductEvents()
    object SaveProduct: AddEditProductEvents()
}
