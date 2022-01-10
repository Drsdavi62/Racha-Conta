package com.example.application.billsplitingapp.ui.presentation.bill.People

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.application.billsplitingapp.ui.presentation.bill.BillViewModel

@Composable
fun PeopleScreen(viewModel: BillViewModel = hiltViewModel()) {
    Text(text = "People")
}