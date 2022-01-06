package com.example.application.billsplitingapp.ui.presentation.history

import com.example.application.billsplitingapp.domain.model.Bill

data class HistoryListState(
    var isLoading: Boolean = false,
    var bills: List<Bill> = emptyList(),
    var error: String = ""
)